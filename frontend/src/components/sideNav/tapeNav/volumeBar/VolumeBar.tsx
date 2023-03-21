import { useEffect, useState } from "react";
import style from "./VolumeBar.module.css";
import sound from "../../../../../public/sound/music_sample_1.wav";

export const VolumeBar = () => {
  const [volume, setVolume] = useState(0.5);
  // Youtube, Socket 음원 소스 어떤 형식으로 주는지 백에 확인하기
  // 사연, 채팅, 곡 소개는 mp3형식 파일로 ==> Web Audio API 가능 할 듯!
  // 그냥 파일 넘겨주는거 받아서 리스트에 넣어서 사용하면 될듯
  // 유튜브는 embed로 ==> <audio> 소스로 이용 방법 고민해보기

  const [music] = useState(new Audio(sound));

  useEffect(() => {
    // console.log(volume);
    music.volume = volume;
    // 음원 재생 로직은 추후에 소켓 만들면서 다시 고민해보기
    music.play();
  }, [volume]);

  return (
    <div className={style.volumeBar}>
      <input
        type="range"
        min={0}
        max={1}
        step={0.01}
        value={volume}
        onChange={(e) => setVolume(e.target.valueAsNumber)}
        className={style.rangeInput}
      ></input>
      <img src="img/volumeBar.png" className={style.bgImg} />
    </div>
  );
};
