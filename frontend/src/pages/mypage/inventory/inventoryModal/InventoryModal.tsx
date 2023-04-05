import style from "./InventoryModal.module.css";
import { v4 as uuidv4 } from "uuid";
import { useState } from "react";
import { Button } from "@/components/common/button/Button";
import { getUserMoney } from "@/connect/axios/queryHooks/user";
import {
  getBackground,
  getBadge,
  getTheme,
  putItem,
} from "@/connect/axios/queryHooks/item";
import { useQueryClient } from "@tanstack/react-query";
import { useCustomToast } from "@/customHooks/useCustomToast";
import Swal from "sweetalert2";

interface InventoryModalProps {
  originSelet: number;
  source: string;
  width: string;
  type: number;
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export const InventoryModal = ({
  originSelet,
  source,
  width,
  type,
  setIsModalOpen,
}: InventoryModalProps) => {
  const queryClient = useQueryClient();
  const { data: userMoney } = getUserMoney();
  const [selected, setSelected] = useState(originSelet);
  const badge = ["none", "red", "skyblue", "green", "gray"];
  const isMobile = () => {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
      navigator.userAgent
    );
  };

  const { data: item } =
    type === 1 ? getBadge() : type === 2 ? getBackground() : getTheme();

  return (
    <div
      className={style.inventory_modal}
      style={isMobile() ? { height: "40vh", width: "80vw" } : undefined}
    >
      <div className={style.item_flex}>
        {item?.map((x, i) => {
          return (
            <div
              key={uuidv4()}
              className={style.img_box}
              style={{ width: width }}
            >
              {type === 1 ? (
                <div
                  className={
                    selected === i + 1
                      ? style.badge + " " + style.selected
                      : style.badge
                  }
                  style={{ backgroundColor: badge[i] }}
                  onClick={() => {
                    setSelected(i + 1);
                  }}
                />
              ) : (
                <img
                  className={
                    selected === i + 1
                      ? style.img + " " + style.selected
                      : style.img
                  }
                  src={`${source}${i + 1}.png`}
                  onClick={() => {
                    setSelected(i + 1);
                  }}
                />
              )}
            </div>
          );
        })}
      </div>
      <div className={style.cal_box}>
        <h4>
          {item?.at(selected - 1)?.badgeName ||
            item?.at(selected - 1)?.backgroundName ||
            item?.at(selected - 1)?.themeName}
        </h4>
        <p>나의 츄르 : {userMoney?.data.userMoney}</p>
        <p>
          {selected === originSelet ? (
            <span>0츄르</span>
          ) : (
            <span className={style.red}>
              -
              {item?.at(selected - 1)?.badgeCost ||
                item?.at(selected - 1)?.backgroundCost ||
                item?.at(selected - 1)?.themeCost}
              츄르
            </span>
          )}
        </p>
        <hr style={{ width: "20vw" }} />
        <p>
          남은 츄르 :
          <span
            className={
              selected !== originSelet &&
              userMoney?.data.userMoney -
                (item?.at(selected - 1)?.badgeCost ||
                  item?.at(selected - 1)?.backgroundCost ||
                  item?.at(selected - 1)?.themeCost ||
                  0) <
                0
                ? style.red
                : undefined
            }
            style={{ marginLeft: "5px" }}
          >
            {selected === originSelet
              ? userMoney?.data.userMoney
              : userMoney?.data.userMoney -
                (item?.at(selected - 1)?.badgeCost ||
                  item?.at(selected - 1)?.backgroundCost ||
                  item?.at(selected - 1)?.themeCost ||
                  0)}
          </span>
        </p>
        <Button
          content="변경 하기"
          onClick={() => {
            putItem(type, selected)
              ?.then(() => {
                queryClient.invalidateQueries(["getUserMoney"]);
                queryClient.invalidateQueries(["getUserConfig"]);
                useCustomToast("success", "아이탬을 변경하였습니다.");
                setIsModalOpen(false);
              })
              .catch(() => {
                Swal.fire({
                  icon: "error",
                  title: "",
                  text: "네트워크 오류 / 다시 시도해 주세요",
                  confirmButtonText: "닫기",
                });
              });
          }}
          style={
            (selected !== originSelet &&
              userMoney?.data.userMoney -
                (item?.at(selected - 1)?.badgeCost ||
                  item?.at(selected - 1)?.backgroundCost ||
                  item?.at(selected - 1)?.themeCost ||
                  0) <
                0) ||
            selected === originSelet
              ? { opacity: "0.5", pointerEvents: "none" }
              : undefined
          }
        />
      </div>
    </div>
  );
};
