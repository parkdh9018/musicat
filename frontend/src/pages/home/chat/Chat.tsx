import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { useEffect, useRef, useState } from "react";
import style from "./Chat.module.css";
import { useTokenData } from "@/customHooks/useTokenData";
import { submit } from "@/connect/socket/socket.chat";
import { useRecoilState, useRecoilValue } from "recoil";
import { changeChatList, chatListState, receivedData, sendData, socketConnection, socketConnectionState } from "@/atoms/socket.atom";
import SocketManager from "@/connect/socket/socket";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUsers } from "@fortawesome/free-solid-svg-icons";

export const Chat = () => {
  // 싱글톤 웹소켓 객채를 소환
  // const socketManager = SocketManager.getInstance();
  // const stompClient = socketManager.connect();

  // const [connected, setConnected] = useRecoilState(socketConnectionState); 
  // const connectOrDisconnect = useRecoilState(socketConnection)[1];
  const data = useRecoilValue(receivedData);

  const socket = socketConnection();

  useEffect(() => {
    if(data) {
      console.log("받음 : ", data)
    }
  },[data]);

  useEffect(() => {
    socket();
  },[])



  const userInfo = useTokenData();

  // 채팅 메세지, 출력될 리스트, 나의 정보
  const [message, setMessage] = useState<string>("");
  const [chatList, setChatList] = useRecoilState(chatListState);
  const messageBoxRef = useRef<HTMLDivElement>(null);

  /** 스크롤을 맨 밑으로 고정 */
  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }
  };

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

  // 채팅창을 맨 아래로 유지시킴
  useEffect(() => {
    scrollToBottom();
  }, [chatList]);

  return (
    <div className={style.chat_component}>
      <div className={style.users}>
        <FontAwesomeIcon icon={faUsers} />
        <div className={style.user_num}>20</div>
      </div>
      <div className={style.chat_list} ref={messageBoxRef}>
        {chatList.map((content) => {
          return content;
        })}
      </div>
      <hr className={style.hr} />
      <div className={style.input_area}>
        <Input
          input={message}
          setInput={setMessage}
          style={{ width: "80%" }}
          onKeyDown={() => {}
            // userInfo && stompClient
            //   ? submit(stompClient, userInfo, setMessage, message)
            //   : undefined
          }
        />
        <Button
          content="전 송"
          style={{ marginLeft: "10px" }}
          onClick={() => {
            sendData({api: "/app/chat", data : { 
              senderSeq:2,
              sender: "paff",
              content: "안녕"
            
            }})

            // userInfo && stompClient
            //   ? submit(stompClient, userInfo, setMessage, message)
            //   : undefined;
          }}
        />
      </div>
    </div>
  );
};
