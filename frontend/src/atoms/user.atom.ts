import { $ } from "@/connect/axios/setting";
import { NavigateFunction } from "react-router-dom";
import { atom, Resetter, useResetRecoilState } from "recoil";

export const userInfoState = atom({
  key: "userInfo",
  default: {
    userSeq: 4,
    userRole: "",
    userProfile: "",
    userNick: "",
  },
});

export function logoutUser(func: Resetter, navigate: NavigateFunction) {
  localStorage.clear();
  $.post("/logout", {}).then(() => {
    func();
    navigate("/");
  });
  console.log("요청 보냈다!!!");
}

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
