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