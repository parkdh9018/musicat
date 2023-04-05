import { nowMainPageState } from "@/atoms/common.atom";
import { TapeNav } from "@/components/sideNav/tapeNav/TapeNav";
import { useEffect } from "react";
import { Outlet } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import style from "./Home.module.css";

export const Home = () => {
  const setNowMainPage = useSetRecoilState(nowMainPageState);

  useEffect(() => {
    setNowMainPage(true);
  }, []);

  return (
    <>
      <div>
        <div className={style.home}>
          <div
            className={style.leftTab}
            style={{ animation: "0.7s ease-in-out loadEffect2" }}
          >
            <TapeNav />
          </div>
          <div
            className={style.rightTab}
            style={{ animation: "0.7s ease-in-out loadEffect3" }}
          >
            <div className={style.content}>
              <Outlet />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
