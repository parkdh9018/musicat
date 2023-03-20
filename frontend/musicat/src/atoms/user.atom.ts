import { atom } from "recoil";

export const memberInfo = atom({
  key: "memberInfo",
  default: {
    memberSeq: 1,
    memberRole: "member",
    memberProfile:
      "https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/5f/9a/4c/f6/5f9a4cf615cfd2738de6.jpg",
    memberNick: "라이온",
  },
});

export const memberThema = atom({
  key: "memberThema",
  default: {
    darkMode: true,
    type1: 0,
    type2: 0,
    type3: 0,
  },
});
