import { useRecoilValue } from "recoil";
import style from "./OnairSign.module.css";
import { useEffect, useState } from "react";
import { broadcastState } from "@/atoms/broadcast.atom";

export const OnairSign = () => {
  const broadcast = useRecoilValue(broadcastState);

  const [title, setTitle] = useState("방송중");

  useEffect(() => {
    if (broadcast.operation == "IDLE") {
      setTitle("휴식중");
    } else if (broadcast.operation == "CHAT") {
      setTitle("소통중");
    } else if (broadcast.operation == "MUSIC") {
      setTitle("음악감상중");
    } else if (broadcast.operation == "STORY") {
      setTitle("사연소개중")
    }
  }, [broadcast]);

  return (
    <div className={style.onairSign}>
      <div className={style.box}>
        <div className={style.logo}>
          <b>
            <span>{title.slice(0, 1)}</span>
            <span>{title.slice(1, 2)}</span>
            <span>{title.slice(2, 10)}</span>
            {/* <span>O</span>N A<span>IR</span> */}
          </b>
        </div>
      </div>
    </div>
  );
};
