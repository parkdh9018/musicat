import { nowMainPageState } from "@/atoms/common.atom";
import { useRecoilValue } from "recoil";
import style from "./Broadcast.module.css";

export const Broadcast = () => {
  const nowMainPage = useRecoilValue(nowMainPageState);

  return (
    <div
      className={
        nowMainPage ? style.broadcast : style.broadcast + " " + style.mypage
      }
    >
      너는 왜 나를 못찾니? dfdfddfd
    </div>
  );
};
