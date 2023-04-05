import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { useEffect, useRef, useState } from "react";
import { useTokenData } from "@/customHooks/useTokenData";
import { useRecoilState } from "recoil";
import { sendData } from "@/atoms/socket.atom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUsers } from "@fortawesome/free-solid-svg-icons";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { chatListState } from "@/atoms/chat.atom";
import { getPeopleCnt } from "@/connect/axios/queryHooks/chat";
import style from "./Chat.module.css";
import { useChatMake } from "@/customHooks/useChatMake";

export const Chat = () => {
  const userInfo = useTokenData();

  // 채팅 메세지, 출력될 리스트, 나의 정보
  const [message, setMessage] = useState<string>("");
  const [chatList] = useRecoilState(chatListState);
  const { data: chatPeople } = getPeopleCnt();
  const messageBoxRef = useRef<HTMLDivElement>(null);

  // 모바일 기기인지 확인해 주는 함수
  const isMobile = () => {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
      navigator.userAgent
    );
  };

  /** 스크롤을 맨 밑으로 고정 */
  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }
  };

  // 채팅창을 맨 아래로 유지시킴
  useEffect(() => {
    scrollToBottom();
  }, [chatList]);

  const clickSubmit = () => {
    if (userInfo?.userIsBan || userInfo?.userIsChattingBan) {
      useCustomToast("error", "권한이 정지된 사용자입니다!");
      return;
    }

    if (!userInfo) {
      useCustomToast("error", "로그인이 필요한 서비스 입니다");
      return;
    }

    if (!message) return;

    sendData("/app/chat", {
      senderSeq: userInfo.userSeq,
      sender: userInfo.userNick,
      content: message,
      badgeSeq: 1,
      isBan: false,
    });

    setMessage("");
  };

  useEffect(() => {
    if (message.length > 50) {
      useCustomToast("warning", "체팅은 50자를 넘을 수 없습니다!!");
      setMessage(message.slice(0, 49));
    }
  }, [message]);

  return (
    <div className={style.chat_component}>
      <div className={style.users}>
        <FontAwesomeIcon icon={faUsers} />
        <div className={style.user_num}>{chatPeople}</div>
      </div>
      <div className={style.chat_list} ref={messageBoxRef}>
        {chatList.map((el) => useChatMake(el, userInfo))}
      </div>
      <hr className={style.hr} />
      <div className={style.input_area}>
        <Input
          input={message}
          setInput={setMessage}
          onKeyDown={clickSubmit}
          style={isMobile() ? { width: "60%" } : { width: "74%" }}
        />
        <Button
          content="전 송"
          style={{ marginLeft: "10px" }}
          onClick={clickSubmit}
        />
      </div>
    </div>
  );
};
