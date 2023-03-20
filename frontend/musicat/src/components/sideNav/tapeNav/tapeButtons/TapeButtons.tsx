import style from "./TapeButtons.module.css";
import { NavLink } from "react-router-dom";
import { Button } from "@/components/common/button/Button";

export const TapeButtons = () => {
  return (
    <div className={style.bg}>
      <Button content="재생" onClick={() => {}} className={style.play} />
      <Button content="정지" onClick={() => {}} className={style.stop} />
      <NavLink
        to="/"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        채팅
      </NavLink>
      <NavLink
        to="/story"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        사연
      </NavLink>
      <NavLink
        to="/songRequest"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        노래
      </NavLink>
      <NavLink
        to="/about"
        className={({ isActive }) =>
          isActive ? style.clicked : style.unclicked
        }
      >
        소개
      </NavLink>
    </div>
  );
};

// 각 버튼의 상태: 클릭상태,  컨텐츠
// 기능: onClick => navigate
