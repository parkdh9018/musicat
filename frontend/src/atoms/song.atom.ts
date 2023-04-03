import { atom } from "recoil";

export interface Music {
  type: string;
  path: string;
  startTime: number;
  playedTime: number;
  length: number;
  artist : string;
  title : string;
  image : string;
}

export const volumeState = atom({
  key: "volume",
  default: 0.5,
});

export const musicState = atom<Music>({
  key: "music",
  default: {
    type: "",
    path: "",
    startTime: 0,
    playedTime: -10,
    length: 0,
    artist : "",
    image : "",
    title : "",
  },
});

export const playNowState = atom({
  key: "playNow",
  default: true,
});
