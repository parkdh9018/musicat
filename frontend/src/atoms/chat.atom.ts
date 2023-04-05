import { atom } from "recoil";

export interface Chat {
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

export function changeChatList(prevChatArr: Chat[], newChat: object) {
  const list = [...prevChatArr, newChat];
  if (list.length > 30) {
    const newList = [];
    for (let i = 1; i < list.length; i++) {
      newList.push(list[i]);
    }
    return newList;
  }
  return list;
}
