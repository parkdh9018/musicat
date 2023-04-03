import { useRecoilValue } from "recoil";
import style from "./OnairSign.module.css";
import { useEffect, useState } from "react";
import { broadcastOperationState } from "@/atoms/broadcast.atom";

export const OnairSign = () => {
  const broadcastOperation = useRecoilValue(broadcastOperationState);

  const [title, setTitle] = useState("방송중");

  useEffect(() => {
    if (broadcastOperation == "IDLE") {
      setTitle("휴식중");
    } else if (broadcastOperation == "CHAT") {
      setTitle("소통중");
    } else if (broadcastOperation == "MUSIC") {
      setTitle("음악감상중");
    } else if (broadcastOperation == "STORY") {
      setTitle("사연소개중")
    }
  }, [broadcastOperation]);

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
