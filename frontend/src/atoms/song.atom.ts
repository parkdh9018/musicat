import { atom } from "recoil";

export interface Music {
  type: string;
  path: string;
  startTime: number;
  playedTime: number;
  length: number;
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
  },
});

export const playNowState = atom({
  key: "playNow",
  default: true,
});

export const nowPlaying = atom({
  key: "nowPlaying",
  default: {
    audioType: "",
    audioSource: "",
    audioStartedAt: 0,
    audioCurrentTime: 0,
    musicSeq: 0,
  },
});
