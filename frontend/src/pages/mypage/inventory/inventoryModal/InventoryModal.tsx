import style from "./InventoryModal.module.css";
import { v4 as uuidv4 } from "uuid";
import { useState } from "react";
import { useRecoilValue } from "recoil";
import { userThemaState } from "@/atoms/user.atom";
import { Button } from "@/components/common/button/Button";

interface InventoryModalProps {
  itemCount: number;
  source: string;
  width: string;
  type: number;
}

export const InventoryModal = ({
  itemCount,
  source,
  width,
  type,
}: InventoryModalProps) => {
  const churu = 130;
  const thema = useRecoilValue(userThemaState);
  const [selected, setSelected] = useState(thema.type2);
  const arr = new Array(itemCount).fill(1);
  const badge = ["none", "red", "skyblue", "green", "gray"];
  return (
    <div className={style.inventory_modal}>
      <div className={style.item_flex}>
        {arr.map((x, i) => {
          return (
            <div
              key={uuidv4()}
              className={style.img_box}
              style={{ width: width }}
            >
              {type === 1 ? (
                <div
                  className={
                    selected === i
                      ? style.badge + " " + style.selected
                      : style.badge
                  }
                  style={{ backgroundColor: badge[i] }}
                  onClick={() => {
                    setSelected(i);
                  }}
                ></div>
              ) : (
                <img
                  className={
                    selected === i
                      ? style.img + " " + style.selected
                      : style.img
                  }
                  src={`${source}${i}.png`}
                  onClick={() => {
                    setSelected(i);
                  }}
                />
              )}
            </div>
          );
        })}
      </div>
      <div className={style.cal_box}>
        <p>나의 츄르 : {churu}</p>
        <p>
          {selected === thema.type2 ? (
            <span>0츄르</span>
          ) : (
            <span className={style.red}>-50츄르</span>
          )}
        </p>
        <hr style={{ width: "20vw" }} />
        <p>
          남은 츄르 :
          <span
            className={
              selected !== thema.type2 && churu - 50 < 0 ? style.red : undefined
            }
            style={{ marginLeft: "5px" }}
          >
            {selected === thema.type2 ? churu : churu - 50}
          </span>
        </p>
        <Button
          content="변경 하기"
          onClick={() => {}}
          style={
            (selected !== thema.type2 && churu - 50 < 0) ||
            selected === thema.type2
              ? { opacity: "0.5", pointerEvents: "none" }
              : undefined
          }
        />
      </div>
    </div>
  );
};
