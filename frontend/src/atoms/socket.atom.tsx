import { ReactElement } from "react";
import { atom, selector, useRecoilCallback } from "recoil";
import { v4 as uuidv4 } from "uuid";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

interface SendDataParams {
  api: string;
  data: object;
}

const SOCKET_BASE_URL = "http://70.12.246.161:9999/ws";

let stompClient: Stomp.Client | null = null;

// 소켓 연결 상태를 관리하는 atom
export const socketConnectionState = atom({
  key: "socketConnectionState",
  default: false,
});

// 소켓 통신에서 받는 데이터를 저장하는 atom
export const receivedData = atom({
  key: "receivedData",
  default: null,
});

// 소켓 연결 및 해제 로직을 관리하는 selector
export const socketConnection = () => {
  const callback = useRecoilCallback(({ set }) => async () => {
    const socket = new SockJS(SOCKET_BASE_URL);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
      stompClient?.subscribe('/topic', (message) => {
        set(receivedData, JSON.parse(message.body));
      });
      stompClient?.subscribe('/user/queue', (message) => {
        set(receivedData, JSON.parse(message.body));
      });
    });
  });

  return callback;
} 

// 데이터 전송
export const sendData = async (params: SendDataParams) => {
  const { data, api } = params;

  if (stompClient && stompClient.connected) {
    stompClient.send(api, {}, JSON.stringify(data));
  } else {
    console.error("소켓이 연결되지 않았습니다.");
  }
};

//////////////////////////////////////////////////////////////////

// 채팅 List
export const chatListState = atom<ReactElement[]>({
  key: "socketState",
  default: [],
});

export function changeChatList(prevChatArr: ReactElement[], newChat: string) {
  // 여기서 newChat에 어떤 CSS를 입히고 badge를 붙인 체팅을 만들지 결정한다.

  const list = [...prevChatArr, <p key={uuidv4()}>{newChat}</p>];
  if (list.length > 30) {
    const newList = [];
    for (let i = 1; i < list.length; i++) {
      newList.push(list[i]);
    }
    return newList;
  }
  return list;
}
