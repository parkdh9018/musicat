import Stomp from "stompjs";

interface ChatUserInfo {
  userSeq: number;
  userNick: string;
}

export function submit(
  stompClient: Stomp.Client,
  userInfo: ChatUserInfo,
  setMessage: (value: React.SetStateAction<string>) => void,
  message: string
) {
  if (message === "") return;

  stompClient.send(
    "/app/chat",
    {},
    JSON.stringify({
      senderSeq: userInfo.userSeq,
      sender: userInfo.userNick,
      badge: 1,
      ban: false,
      content: message,
    })
  );
  setMessage("");
}
