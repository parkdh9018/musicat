import { Link } from "react-router-dom";
import style from "./Popover.module.css";

export const Popover = () => {
  return (
    <div className={style.popover}>
      <div className={style.triangle}></div>
      <div className={style.content}>
        <Link to="/mypage/myinfo"><div className={style.content_text}>나의 정보 관리</div></Link>
        <Link to="/mypage/notice"><div className={style.content_text}>알림 / 공지사항</div></Link>
        <Link to="/mypage/inventory"><div className={style.content_text}>인벤토리</div></Link>
        {/* TODO : 로그아웃 구현 */}
        <Link to=""><div className={style.content_text}>로그아웃</div></Link>
      </div>

    </div>
  );
};
