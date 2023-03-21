import { nowSideNav } from "@/atoms/common.atom";
import { userThemaState } from "@/atoms/user.atom";
import { Modal } from "@/components/common/modal/Modal";
import { useEffect, useState } from "react";
import { useRecoilValue, useSetRecoilState } from "recoil";
import style from "./Inventory.module.css";
import { InventoryModal } from "./inventoryModal/InventoryModal";

export const Inventory = () => {
  const setNowSideNav = useSetRecoilState(nowSideNav);
  const thema = useRecoilValue(userThemaState);
  const [isModalOpen, setIsModalOpen] = useState(false);

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("인벤토리");
  }, []);

  return (
    <div className={style.inventory}>
      <div
        className={style.content_div}
        style={{ marginTop: "0", minHeight: "40px" }}
      >
        <span>배지 장착 :</span>
        {thema.type1 === "none" ? (
          <span
            className={style.badge_span}
            onClick={() => {
              setIsModalOpen(true);
            }}
          >
            배지 없음
          </span>
        ) : (
          <div
            className={style.badge}
            style={{ backgroundColor: `${thema.type1}` }}
            onClick={() => {
              setIsModalOpen(true);
            }}
          />
        )}

        <div style={{ clear: "both" }} />
      </div>
      <div className={style.content_div} style={{ minHeight: "11vw" }}>
        <span>배경 설정 :</span>
        <img
          className={style.img1}
          src={`/img/background/background${thema.type2}.png`}
          onClick={() => {
            setIsModalOpen(true);
          }}
        />
        <div style={{ clear: "both" }} />
      </div>
      <div className={style.content_div}>
        <span>테마 설정 :</span>
        <img
          className={style.img2}
          src={`/img/thema/thema${thema.type3}.png`}
          onClick={() => {
            setIsModalOpen(true);
          }}
        />
        <div style={{ clear: "both" }} />
      </div>
      {isModalOpen && (
        <Modal
          setModalOpen={setIsModalOpen}
          children={
            <InventoryModal
              itemCount={6}
              source={"/img/thema/thema"}
              width={"20%"}
              thema={2}
            />
          }
        />
      )}
    </div>
  );
};
