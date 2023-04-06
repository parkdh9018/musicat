import { useRecoilState, useRecoilValue, useResetRecoilState } from "recoil";
import { GraphicCanvas } from "./graphicCanvas/GraphicCanvas";
import { RadioPlayer } from "./radioPlayer/RadioPlayer";
import { socketConnection } from "@/atoms/socket.atom";
import { nowMainPageState } from "@/atoms/common.atom";
import { useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPlayCircle,
  faStopCircle,
} from "@fortawesome/free-regular-svg-icons";
import { faArrowUpRightFromSquare } from "@fortawesome/free-solid-svg-icons";
import { playNowState } from "@/atoms/song.atom";
import style from "./Broadcast.module.css";
import SocketManager from "@/connect/socket/socket";
import { musicState } from "@/atoms/song.atom";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "@tanstack/react-query";

export const Broadcast = () => {
  const queryClinet = useQueryClient();
  const nowMainPage = useRecoilValue(nowMainPageState);
  const [playNow, setPlayNow] = useRecoilState(playNowState);
  const socket = socketConnection(queryClinet);
  const stopRadio = useResetRecoilState(musicState);
  const navigate = useNavigate();
  const socketManager = SocketManager.getInstance();

  useEffect(() => {
    if (playNow) socket();
  }, [playNow]);

  return (
    <div
      className={
        nowMainPage ? style.broadcast : style.broadcast + " " + style.mypage
      }
    >
      <div
        className={style.broad_back}
        style={nowMainPage ? { display: "none" } : undefined}
      >
        <FontAwesomeIcon
          icon={faPlayCircle}
          className={style.play}
          style={!playNow ? undefined : { display: "none" }}
          onClick={() => {
            setPlayNow(true);
          }}
        />
        <FontAwesomeIcon
          icon={faStopCircle}
          className={style.play}
          style={playNow ? undefined : { display: "none" }}
          onClick={() => {
            setPlayNow(false);
            stopRadio();
            socketManager.disconnect();
          }}
        />
        <FontAwesomeIcon
          icon={faArrowUpRightFromSquare}
          className={style.share}
          onClick={() => {
            navigate("/");
          }}
        />
      </div>
      <GraphicCanvas />
      <RadioPlayer />
    </div>
  );
};
