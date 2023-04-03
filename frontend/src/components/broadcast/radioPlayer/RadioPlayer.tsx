import ReactPlayer from "react-player";
import { musicState, volumeState } from "@/atoms/song.atom";
import { useRecoilValue } from "recoil";
import { useRef } from "react";

export const RadioPlayer = () => {
  const volume = useRecoilValue(volumeState);
  const music = useRecoilValue(musicState);
  const playerRef = useRef<ReactPlayer>(null);

  return (
    <div style={{ visibility: "hidden" }}>
      <ReactPlayer
        url={music.path}
        controls
        onProgress={(state) => {
          if (state.playedSeconds < Math.floor(music.playedTime / 1000)) {
            console.log("검사");
            console.log(Math.floor(music.playedTime / 1000));
            playerRef.current?.seekTo(
              Math.floor(music.playedTime / 1000),
              "seconds"
            );
          }
        }}
        playing={true}
        volume={volume}
        ref={playerRef}
        width={0}
        height={0}
      />
    </div>
  );
};
