import { atom } from "recoil";

export const userInfoState = atom({
  key: "userInfo",
  default: {
    userSeq: 1,
    userRole: "user",
    userProfile:
      "https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/5f/9a/4c/f6/5f9a4cf615cfd2738de6.jpg",
    userNick: "라이온",
  },
});

// 나중에 리엑트 쿼리로 대체
export const userThemaState = atom({
  key: "userThemaState",
  default: {
    darkMode: false,
    type1: 0,
    type2: 0,
    type3: 0,
  },
});

// 안 읽은 메세지
// 츄르의 개수
// => 리엑트 쿼리로 읽자
