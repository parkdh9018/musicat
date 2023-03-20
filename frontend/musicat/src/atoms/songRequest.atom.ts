import { atom } from "recoil";

export const contentstate = atom({
  key: "content",
  default: {

  }
});

export const popoverstate = atom({
  key: "popover",
  default: false,
})