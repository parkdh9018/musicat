import { nowMainPageState } from "@/atoms/common.atom";
import { Broadcast } from "@/components/broadcast/Broadcast";
import { Header } from "@/components/header/Header";
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
      <Header />
      <div
        style={{
          backgroundImage: "url(/img/pagebackground/theme1.png)",
          width: "100%",
        }}
      >
        <Broadcast />
      </div>
      <div
        style={{
          backgroundImage: "url(/img/pagebackground/theme1.png)",
          width: "100%",
        }}
      >
        <div className={style.home}>
          <div className={style.leftTab}>
            <TapeNav />
          </div>
          <div className={style.rightTab}>
            <div className={style.content}>
              <Outlet />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};