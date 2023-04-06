import ReactPlayer from "react-player";
import { musicState, playNowState, volumeState } from "@/atoms/song.atom";
import { useRecoilValue } from "recoil";
import { useRef } from "react";
import { broadcastState } from "@/atoms/broadcast.atom";

export const RadioPlayer = () => {
  const volume = useRecoilValue(volumeState);
  const music = useRecoilValue(musicState);
  const broadcast = useRecoilValue(broadcastState);
  const playerRef1 = useRef<ReactPlayer>(null);
  const playerRef2 = useRef<ReactPlayer>(null);
  const playNow = useRecoilValue(playNowState);

  return (
    <div style={{ visibility: "hidden" }}>
      {/* 일반적인 노래 / */}
      <ReactPlayer
        url={music.path}
        controls
        onStart={() => {
          playerRef1.current?.seekTo(
            Math.floor(music.playedTime / 1000),
            "seconds"
          );
        }}
        playing={true}
        volume={volume}
        ref={playerRef1}
        width={0}
        height={0}
        playsinline={true}
        webkit-playsinline="true"
      />
      {/* 채팅시간 배경음악용 플레이어 */}
      <ReactPlayer
        url={
          broadcast.operation === "CHAT" && playNow
            ? "https://youtu.be/xRqPBkklonM"
            : undefined
        }
        controls
        onStart={() => {
          playerRef2.current?.seekTo(
            Math.floor(music.typePlayedTime / 1000),
            "seconds"
          );
        }}
        playing={broadcast.operation === "CHAT" && playNow ? true : false}
        volume={volume / 4}
        ref={playerRef2}
        width={0}
        height={0}
        playsinline={true}
        webkit-playsinline="true"
      />
    </div>
  );
};
