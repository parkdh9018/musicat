import style from "./TapeButtons.module.css";
import { NavLink } from "react-router-dom";
import { Button } from "@/components/common/button/Button";
import { useRecoilState, useResetRecoilState } from "recoil";
import { musicState, playNowState } from "@/atoms/song.atom";
import SocketManager from "@/connect/socket/socket";
import { socketConnection } from "@/atoms/socket.atom";

export const TapeButtons = () => {
  const [playNow, setPlayNow] = useRecoilState(playNowState);
  const stopRadio = useResetRecoilState(musicState);
  const socket = SocketManager.getInstance();

  return (
    <div className={style.bg}>
      <Button
        content="재생"
        onClick={() => {
          setPlayNow(true);
        }}
        className={!playNow ? style.play : style.play_clicked}
      />
      <Button
        content="정지"
        onClick={() => {
          setPlayNow(false);
          stopRadio();
          socket.disconnect();
        }}
        className={playNow ? style.stop : style.stop_clicked}
      />
      <NavLink
        to="/"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        채팅
      </NavLink>
      <NavLink
        to="/story"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        사연
      </NavLink>
      <NavLink
        to="/songRequest"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        노래
      </NavLink>
      <NavLink
        to="/about"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        소개
      </NavLink>
    </div>
  );
};
