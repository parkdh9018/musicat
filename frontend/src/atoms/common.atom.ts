import { atom } from "recoil";

export const nowSideNavState = atom({
  key: "nowSideNavState",
  default: "",
});

export const nowMainPageState = atom({
  key: "nowMainPageState",
  default: true,
});
