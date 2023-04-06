import { useEffect, useState } from "react";
import style from "./CDplayer.module.css";
import { useRecoilState, useRecoilValue } from "recoil";
import { musicState, playNowState } from "@/atoms/song.atom";

export const CDplayer = () => {
  const [backGroundImage, setBackGroundImage] = useState("");
  const musicData = useRecoilValue(musicState);
  const [playNow] = useRecoilState(playNowState);

  useEffect(() => {
    setBackGroundImage(musicData.image || "/img/tape/init.png");
  }, [musicData]);

  return (
    <div className={style.cd_box}>
      <div
        className={style.cd}
        style={playNow ? undefined : { animation: "none" }}
      >
        <div
          className={style.cd2}
          style={{ backgroundImage: `url(${backGroundImage})` }}
        />
        <div className={style.cd3} />
      </div>
    </div>
  );
};
