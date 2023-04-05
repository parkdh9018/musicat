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
        onStart={() => {
          playerRef.current?.seekTo(
            Math.floor(music.playedTime / 1000),
            "seconds"
          );
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
