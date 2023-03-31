import ReactPlayer from "react-player";
import { musicState, volumeState } from "@/atoms/song.atom";
import { useRecoilValue } from "recoil";
import { useRef } from "react";

export const RadioPlayer = () => {
  const volume = useRecoilValue(volumeState);
  const music = useRecoilValue(musicState);
  const playerRef = useRef<ReactPlayer>(null);

  return (
    <div
      style={{ visibility: "hidden", display: !music.type ? "block" : "none" }}
    >
      <ReactPlayer
        url={music.path}
        controls
        onProgress={(state) => {
          if (state.playedSeconds < Math.floor(music.playedTime / 1000)) {
            playerRef.current?.seekTo(
              Math.floor(music.playedTime / 1000),
              "seconds"
            );
            playerRef.current?.getInternalPlayer().play();
          }
        }}
        playing={true}
        volume={volume}
        ref={playerRef}
      />
    </div>
  );
};
