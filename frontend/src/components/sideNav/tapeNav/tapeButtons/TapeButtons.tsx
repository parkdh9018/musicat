import style from "./TapeButtons.module.css";
import { NavLink } from "react-router-dom";
import { Button } from "@/components/common/button/Button";

export const TapeButtons = () => {
  // 재생, 정지 버튼에 소켓 연결, 해제 기능 연결 필요
  // 정지 버튼은 clicked 스타일 적용 필요

  return (
    <div className={style.bg}>
      <Button
        content="재생"
        onClick={() => {
          return;
        }}
        className={style.play}
      />
      <Button
        content="정지"
        onClick={() => {
          return;
        }}
        className={style.stop}
      />
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
