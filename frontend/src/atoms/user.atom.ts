import { atom } from "recoil";

export const userInfoState = atom({
  key: "userInfo",
  default: {
    userSeq: 0,
    userRole: "",
    userProfile: "",
    userNick: "",
  },
});

// 나중에 리엑트 쿼리로 대체
export const userthemeState = atom({
  key: "userthemeState",
  default: {
    darkMode: false,
    type1: 1,
    type2: 1,
    type3: 1,
  },
});

// 안 읽은 메세지
// 츄르의 개수
// => 리엑트 쿼리로 읽자
