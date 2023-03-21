import { atom } from "recoil";

export const userInfoState = atom({
  key: "userInfo",
  default: {
    userSeq: 1,
    userRole: "admin",
    userProfile:
      "https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/5f/9a/4c/f6/5f9a4cf615cfd2738de6.jpg",
    userNick: "라이온",
  },
});

// 너도 useQuery로 대채될듯
export const memberThema = atom({
  key: "memberThema",
  default: {
    darkMode: false,
    type1: 0,
    type2: 0,
    type3: 0,
  },
});
