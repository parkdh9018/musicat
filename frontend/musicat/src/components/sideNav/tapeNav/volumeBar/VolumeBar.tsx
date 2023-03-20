import { useEffect, useState } from "react";
import style from "./VolumeBar.module.css";

export const VolumeBar = () => {
  // spotify 볼륨조절 api end point : 소켓? spotify? 아마도 소켓,,,,(사연, 채팅 등)
  // https://developer.spotify.com/documentation/web-api/reference/#/operations/set-volume-for-users-playback

  const [volume, setVolume] = useState(50);

  return (
    <div className={style.volumeBar}>
      <input
        type="range"
        min="1"
        max="100"
        value={volume}
        onChange={(e) => setVolume(e.target.valueAsNumber)}
        className={style.rangeInput}
      ></input>
      <img src="img/volumeBar.png" className={style.bgImg} />
    </div>
  );
};
