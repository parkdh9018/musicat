import { useRecoilValue } from "recoil";
import { GraphicCanvas } from "./graphicCanvas/GraphicCanvas";
import { RadioPlayer } from "./radioPlayer/RadioPlayer";
import { socketConnection } from "@/atoms/socket.atom";
import { nowMainPageState } from "@/atoms/common.atom";
import { useEffect } from "react";
import { playNowState } from "@/atoms/song.atom";
import style from "./Broadcast.module.css";
import radioBoothIMG from "@/asset/img/radioBooth.png";

export const Broadcast = () => {
  const nowMainPage = useRecoilValue(nowMainPageState);
  const playNow = useRecoilValue(playNowState);
  const socket = socketConnection();

  useEffect(() => {
    if (playNow) socket();
  }, [playNow]);

  return (
    <div
      className={
        nowMainPage ? style.broadcast : style.broadcast + " " + style.mypage
      }
    >
      {/* <img src={radioBoothIMG} alt="" className={style.radioBooth} /> */}
      <GraphicCanvas />
      <RadioPlayer />
    </div>
  );
};
