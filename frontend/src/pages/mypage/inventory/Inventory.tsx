import { nowSideNavState } from "@/atoms/common.atom";
import { userthemeState } from "@/atoms/user.atom";
import { Modal } from "@/components/common/modal/Modal";
import { useEffect, useState } from "react";
import { useRecoilValue, useSetRecoilState } from "recoil";
import style from "./Inventory.module.css";
import { InventoryModal } from "./inventoryModal/InventoryModal";

export const Inventory = () => {
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const theme = useRecoilValue(userthemeState);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalData, setModalData] = useState({
    itemCount: 0,
    source: "",
    width: "",
    type: 0,
  });
  const badge = ["none", "red", "skyblue", "green", "gray"];

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
        {theme.type1 === 0 ? (
          <span
            className={style.badge_span}
            onClick={() => {
              setModalData({
                itemCount: 5,
                source: "",
                width: "10%",
                type: 1,
              });
              setIsModalOpen(true);
            }}
          >
            배지 없음
          </span>
        ) : (
          <div
            className={style.badge}
            style={{ backgroundColor: `${badge[theme.type1]}` }}
            onClick={() => {
              setModalData({
                itemCount: 5,
                source: "",
                width: "10%",
                type: 1,
              });
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
          src={`/img/background/background${theme.type2}.png`}
          onClick={() => {
            setModalData({
              itemCount: 6,
              source: "/img/background/background",
              width: "40%",
              type: 2,
            });
            setIsModalOpen(true);
          }}
        />
        <div style={{ clear: "both" }} />
      </div>
      <div className={style.content_div}>
        <span>테마 설정 :</span>
        <img
          className={style.img2}
          src={`/img/theme/theme${theme.type3}.png`}
          onClick={() => {
            setModalData({
              itemCount: 6,
              source: "/img/theme/theme",
              width: "20%",
              type: 3,
            });
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
              itemCount={modalData.itemCount}
              source={modalData.source}
              width={modalData.width}
              type={modalData.type}
            />
          }
        />
      )}
    </div>
  );
};
