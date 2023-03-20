import { atom } from "recoil";

export const contentstate = atom({
  key: "content",
  default: {
    storyTitle: "",
    storyContent: "",
  }
});