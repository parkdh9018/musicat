import { MypageNav } from "@/components/sideNav/mypageNav/MypageNav";
import { Outlet } from "react-router-dom";
import style from "./Mypage.module.css";

export const Mypage = () => {
  return (
    <div className={style.mypage}>
      <div className={style.leftTab}>
        <MypageNav />
      </div>
      <div className={style.rightTab}>
        <div className={style.content}>
          <Outlet />
        </div>
      </div>
    </div>
  );
};
