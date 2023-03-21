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
  thema: number;
}

export const InventoryModal = ({
  itemCount,
  source,
  width,
}: InventoryModalProps) => {
  const churu = 40;
  const thema = useRecoilValue(userThemaState);
  const [selected, setSelected] = useState(thema.type2);
  const arr = new Array(itemCount).fill(1);
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
              <img
                className={
                  selected === i ? style.img + " " + style.selected : style.img
                }
                src={`${source}${i}.png`}
                onClick={() => {
                  setSelected(i);
                }}
              />
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
            selected !== thema.type2 && churu - 50 < 0
              ? { opacity: "0.5", pointerEvents: "none" }
              : undefined
          }
        />
      </div>
    </div>
  );
};
