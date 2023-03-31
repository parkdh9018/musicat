import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { useEffect, useRef, useState } from "react";
import style from "./Chat.module.css";
import { useTokenData } from "@/customHooks/useTokenData";
import { submit } from "@/connect/socket/socket.chat";
import { useRecoilState, useRecoilValue } from "recoil";
import { sendData, socketConnection } from "@/atoms/socket.atom";
import SocketManager from "@/connect/socket/socket";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUsers } from "@fortawesome/free-solid-svg-icons";
import { v4 as uuidv4 } from "uuid";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { chatListState } from "@/atoms/chat.atom";
import { getPeopleCnt } from "@/connect/axios/queryHooks/chat";

export const Chat = () => {
  // 싱글톤 웹소켓 객채를 소환
  // const socketManager = SocketManager.getInstance();
  // const stompClient = socketManager.connect();

  const socket = socketConnection();
  useEffect(() => {
    socket();
  }, []);

  const userInfo = useTokenData();

  // 채팅 메세지, 출력될 리스트, 나의 정보
  const [message, setMessage] = useState<string>("");
  const [chatList, setChatList] = useRecoilState(chatListState);
  const [peopleCnt, setPeopleCnt] = useState(0);

  const messageBoxRef = useRef<HTMLDivElement>(null);
  const cntRef = useRef<HTMLDivElement>(null);

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

  // 주기적으로 사용자 숫자 불러옴

  useEffect(() => {
    getPeopleCnt.then(({ data }) => {
      setPeopleCnt(data);
    });
    const interval = setInterval(() => {
      getPeopleCnt.then(({ data }) => {
        setPeopleCnt(data);
      });
    }, 5000); // 5초 간격으로 실행

    return () => {
      clearInterval(interval);
    };
  }, []);

  const clickSubmit = () => {
    if (!userInfo) {
      useCustomToast("error", "로그인이 필요한 서비스 입니다");
      return;
    }

    sendData("/app/chat", {
      senderSeq: userInfo.userSeq,
      sender: userInfo.userNick,
      content: message,
      badgeSeq: 1,
      isBan: false,
    });

    setMessage("");
  };

  // useEffect(() => {
  //   if(chatList.length > 0)
  //     console.log(chatList)
  // },[chatList])

  // useEffect(() => {
  //   if (!userInfo) return;

  //   stompClient.connect({}, () => {
  //     console.log("websocket connect");

  //     /** 다른 사람이 채팅을 치면 일어날 일 */
  //     stompClient.subscribe(`/topic/messages`, (data) => {
  //       const newMessage = JSON.parse(data.body);

  //       // 여기서는 newMessage를 파라미터로 넣어서 로직 함수를 집어넣으면 좋을 것 같다.
  //       // 근데 이부분은 백앤드랑 주고받는 데이터 종류를 확정하는게 필요

  //       // 내가 보낸 메시지라면
  //       if (newMessage.senderSeq === Number(userInfo.userSeq)) {
  //         console.log("내가보낸 매세지임");
  //         setChatList((prev) =>
  //           changeChatList(prev, `${newMessage.sender} : ${newMessage.content}`)
  //         );
  //       }

  //       // 다른 사람이 보낸 메시지라면
  //       else {
  //         console.log("다른사람 메세지임");
  //         setChatList((prev) =>
  //           changeChatList(prev, `${newMessage.sender} : ${newMessage.content}`)
  //         );
  //       }
  //     });
  //   });
  // }, []);

  return (
    <div className={style.chat_component}>
      <div className={style.users}>
        <FontAwesomeIcon icon={faUsers} />
        <div className={style.user_num} ref={cntRef}>
          {peopleCnt}
        </div>
      </div>
      <div className={style.chat_list} ref={messageBoxRef}>
        {chatList.map((v) => (
          <p key={uuidv4()}>
            {v.sender} : {v.content}
          </p>
        ))}
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
