import { useRecoilValue } from "recoil";
import style from "./OnairSign.module.css";
import { useEffect } from "react";
import { broadcastOperationState } from "@/atoms/broadcast.atom";

export const OnairSign = () => {
  const broadcastOperation = useRecoilValue(broadcastOperationState);

  useEffect(() => {}, [broadcastOperation]);

  return (
    <div className={style.onairSign}>
      <div className={style.box}>
        <div className={style.logo}>
          <b>
            <span>{broadcastOperation.slice(0, 1)}</span>
            <span>{broadcastOperation.slice(1, 2)}</span>
            <span>{broadcastOperation.slice(2, 10)}</span>
            {/* <span>O</span>N A<span>IR</span> */}
          </b>
        </div>
      </div>
    </div>
  );
};
