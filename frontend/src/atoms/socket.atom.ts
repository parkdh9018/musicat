import { nowMainPageState } from "@/atoms/common.atom";
import { useRecoilCallback, useRecoilValue } from "recoil";
import { changeChatList, Chat, chatListState } from "./chat.atom";
import SocketManager from "@/connect/socket/socket";
import { Music, musicState, playNowState } from "./song.atom";
import { broadcastState } from "./broadcast.atom";
import { QueryClient, useQueryClient } from "@tanstack/react-query";

interface BaseResponse {
  type: string;
  operation: string;
  data: Music;
}

const socketManager = SocketManager.getInstance();
let stompClient = socketManager.connect();

// 소켓 연결
export const socketConnection = (queryClinet: QueryClient) => {
  const callback = useRecoilCallback(
    ({ set }) =>
      async () => {
        // 스톰프 디버그 모드 끄기 ( 콘솔창에 뜨는거)
        stompClient.debug = () => {
          return null;
        };
        stompClient = socketManager.connect();

        stompClient.connect({}, () => {
          console.log("연결 시작");

          stompClient.subscribe("/topic", (message) => {
            dataClassification(set, JSON.parse(message.body), queryClinet);
          });

          stompClient.subscribe("/user/queue", (message) => {
            dataClassification(set, JSON.parse(message.body), queryClinet);
          });

          stompClient.send("/app/subscribe", {}, "");
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
const dataClassification = (
  set: any,
  res: BaseResponse,
  queryClinet: QueryClient
): void => {
  switch (res.type) {
    case "CHAT":
      console.log("나는 챗");
      console.log(res);
      set(chatListState, (prev: Chat[]) => changeChatList(prev, res.data));
      break;
    case "RADIO":
      console.log("나는 라디오");
      console.log(res);
      if (res.data.type === "youtube") {
        console.log(res.data.type);
        queryClinet.invalidateQueries(["SongRequset"]);
        queryClinet.invalidateQueries(["AlredayRegistedSong"]);
      }
      set(broadcastState, {
        operation: res.operation.toUpperCase(),
        dataType: res.data.type,
        dataLength: res.data.length,
      });
      set(musicState, () => res.data);
      break;
    default:
      undefined;
      break;
  }
};
