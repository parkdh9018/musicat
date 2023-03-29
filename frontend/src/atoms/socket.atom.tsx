import { ReactElement } from "react";
import { atom } from "recoil";
import { v4 as uuidv4 } from "uuid";

// 채팅 List
export const chatListState = atom<ReactElement[]>({
  key: "socketState",
  default: [],
});

export function changeChatList(prevChatArr: ReactElement[], newChat: string) {
  // 여기서 newChat에 어떤 CSS를 입히고 badge를 붙인 체팅을 만들지 결정한다.

  let list = [...prevChatArr, <p key={uuidv4()}>{newChat}</p>];
  if (list.length > 30) {
    list = list.splice(0, 31);
  }
  return list;
}
