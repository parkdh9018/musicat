import ReactPlayer from "react-player";
import { musicState, volumeState } from "@/atoms/song.atom";
import { useRecoilValue } from "recoil";
import { useEffect, useRef } from "react";

export const RadioPlayer = () => {
  const volume = useRecoilValue(volumeState);
  const music = useRecoilValue(musicState);
  const playerRef = useRef<ReactPlayer>(null);
  const audioRef = useRef<HTMLAudioElement>(null);

  useEffect(() => {
    if (audioRef.current !== null) {
      console.log("오디오 파일 준비됨");
      console.log(audioRef.current.currentTime);
      console.log(Math.floor(music.playedTime / 1000));
      audioRef.current.currentTime = Math.floor(music.playedTime / 1000);
      console.log(audioRef.current.currentTime);
      audioRef.current.play();
      console.log("이제 재생이 되냐?");
    }
  }, [music]);

  return (
    <div style={{ visibility: "hidden" }}>
      <ReactPlayer
        url={music.path[music.path.length - 1] === "3" ? "null" : music.path}
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
      <audio controls ref={audioRef} src={music.path} />
    </div>
  );
};
