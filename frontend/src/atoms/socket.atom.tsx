import { ReactElement } from "react";
import { atom, selector, SetRecoilState, useRecoilCallback } from "recoil";
import { v4 as uuidv4 } from "uuid";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

interface BaseResponse {
  type: string;
  operation: string;
  data: object;
}

const SOCKET_BASE_URL = "https://musicat.kr/api/ws";

let stompClient: Stomp.Client | null = null;

// 소켓 연결
export const socketConnection = () => {
  const callback = useRecoilCallback(
    ({ set }) =>
      async () => {
        if (stompClient) return;

        const socket = new SockJS(SOCKET_BASE_URL);
        stompClient = Stomp.over(socket);

        // 스톰프 디버그 모드 끄기 ( 콘솔창에 뜨는거)
        stompClient.debug = () => {
          return null;
        };

        stompClient.connect({}, () => {
          stompClient?.subscribe("/topic", (message) => {
            dataClassification(set, JSON.parse(message.body));
          });
          stompClient?.subscribe("/user/queue", (message) => {
            dataClassification(set, JSON.parse(message.body));
          });

          stompClient?.send("/app/subscribe", {}, "");
        });
      },
    []
  );

  return callback;
};

// 데이터 전송
export const sendData = async (api: string, data: object) => {
  if (stompClient && stompClient.connected) {
    stompClient.send(api, {}, JSON.stringify(data));
  } else {
    console.error("소켓이 연결되지 않았습니다.");
  }
};


// 데이터 분류
const dataClassification = (set: any, res: BaseResponse) => {
  switch (res.type) {
    case "CHAT":
      console.log("나는 챗")
      set(chatListState,(prev:Chat[]) => [...prev, res.data])
      break;
    case "RADIO":
      console.log("나는 라디오")
      console.log(res.data)
      break;
    default:
      break;
  }
};



//////////////////////////////////////////////////////////////////
interface Chat {
  senderSeq: number;
  sender: string;
  badgeSeq: number;
  content: string;
  ban: boolean;
}

// 채팅 List
export const chatListState = atom<Chat[]>({
  key: "socketState",
  default: [],
});

// export function changeChatList(prevChatArr: ReactElement[], newChat: string) {
//   // 여기서 newChat에 어떤 CSS를 입히고 badge를 붙인 체팅을 만들지 결정한다.

//   const list = [...prevChatArr, <p key={uuidv4()}>{newChat}</p>];
//   if (list.length > 30) {
//     const newList = [];
//     for (let i = 1; i < list.length; i++) {
//       newList.push(list[i]);
//     }
//     return newList;
//   }
//   return list;
// }
