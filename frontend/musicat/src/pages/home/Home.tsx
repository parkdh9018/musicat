import { TapeNav } from "@/components/sideNav/tapeNav/TapeNav";
import { Outlet } from "react-router-dom";
import style from "./Home.module.css";

export const Home = () => {
  return (
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
  );
};
