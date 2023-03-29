import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import style from "./Chat.module.css";
import { useTokenData } from "@/customHooks/useTokenData";
import { submit } from "@/connect/socket/socket.chat";

export const Chat = () => {
  // const sockJS = new SockJS("https://musicat.kr/api/ws");
  const sockJS = new SockJS("http://70.12.246.161:9999/ws");
  const stompClient = Stomp.over(sockJS);

  // 채팅 메세지, 출력될 리스트, 나의 정보
  const [message, setMessage] = useState<string>("");
  const [msgList, setMsgList] = useState<string[]>([]);
  const messageBoxRef = useRef<HTMLDivElement>(null);
  const userInfo = useTokenData();

  /** 스크롤을 맨 밑으로 고정 */
  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }
  };

  useEffect(() => {
    stompClient.connect({}, () => {
      console.log("websocket connect");

      /** 다른 사람이 채팅을 치면 일어날 일 */
      stompClient.subscribe(`/topic/messages`, (data) => {
        const newMessage = JSON.parse(data.body);

        // 내가 보낸 메시지라면
        console.log(typeof newMessage.senderSeq);
        console.log(typeof userInfo.userSeq);
        console.log(newMessage.senderSeq);
        console.log(userInfo.userSeq);
        if (newMessage.senderSeq === userInfo.userSeq) {
          console.log("내가보낸 매세지임");
          setMsgList((prev) => [
            ...prev,
            `${newMessage.sender} : ${newMessage.content}`,
          ]);
        }
        // 다른 사람이 보낸 메시지라면
        else {
          console.log("다른사람 메세지임");
          setMsgList((prev) => [
            ...prev,
            `${newMessage.sender} : ${newMessage.content}`,
          ]);
        }
      });
    });
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [msgList]);

  return (
    <div className={style.chat_component}>
      <div className={style.chat_list} ref={messageBoxRef}>
        {msgList.map((content) => {
          return <p key={uuidv4()}>{content}</p>;
        })}
      </div>
      <hr className={style.hr} />
      <div className={style.input_area}>
        <Input
          input={message}
          setInput={setMessage}
          style={{ width: "80%" }}
          onKeyDown={() => submit(stompClient, userInfo, setMessage, message)}
        />
        <Button
          content="전 송"
          style={{ marginLeft: "10px" }}
          onClick={() => {
            submit(stompClient, userInfo, setMessage, message);
          }}
        />
      </div>
    </div>
  );
};
