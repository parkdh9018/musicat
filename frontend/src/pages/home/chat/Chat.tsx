import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { useEffect, useRef, useState } from "react";
import { useTokenData } from "@/customHooks/useTokenData";
import { useRecoilState } from "recoil";
import { sendData, socketConnection } from "@/atoms/socket.atom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUsers } from "@fortawesome/free-solid-svg-icons";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { chatListState } from "@/atoms/chat.atom";
import { getPeopleCnt } from "@/connect/axios/queryHooks/chat";
import style from "./Chat.module.css";
import { useChatMake } from "@/customHooks/useChatMake";

export const Chat = () => {
  const socket = socketConnection();
  const userInfo = useTokenData();

  // 채팅 메세지, 출력될 리스트, 나의 정보
  const [message, setMessage] = useState<string>("");
  const [chatList] = useRecoilState(chatListState);
  const { data: chatPeople } = getPeopleCnt();

  const messageBoxRef = useRef<HTMLDivElement>(null);

  /** 스크롤을 맨 밑으로 고정 */
  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }
  };

  useEffect(() => {
    socket();
  }, []);

  // 채팅창을 맨 아래로 유지시킴
  useEffect(() => {
    scrollToBottom();
  }, [chatList]);

  const clickSubmit = () => {
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
          style={{ width: "80%" }}
          onKeyDown={clickSubmit}
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
