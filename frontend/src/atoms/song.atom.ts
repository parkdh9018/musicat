import { atom } from "recoil";

export const volumeState = atom({
  key: "volume",
  default: 0.5,
});
