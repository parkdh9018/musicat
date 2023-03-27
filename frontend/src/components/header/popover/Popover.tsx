import { userInfoState } from "@/atoms/user.atom";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import style from "./Popover.module.css";

export const Popover = () => {
  const userInfo = useRecoilValue(userInfoState);
  // TODO : 로그아웃 구현 필요
  const userComponent = (
    <>
      <Link to="/mypage/myinfo">
        <div className={style.content_text}>나의 정보 관리</div>
      </Link>
      <Link to="/mypage/notice">
        <div className={style.content_text}>알림 / 공지사항</div>
      </Link>
      <Link to="/mypage/inventory">
        <div className={style.content_text}>인벤토리</div>
      </Link>
      <Link to="">
        <div className={style.content_text}>로그아웃</div>
      </Link>
    </>
  );

  const adminComponent = (
    <>
      <Link to="/mypage/notice-manage">
        <div className={style.content_text}>공지사항</div>
      </Link>
      <Link to="/mypage/user-manage">
        <div className={style.content_text}>유저관리</div>
      </Link>
      <Link to="">
        <div className={style.content_text}>로그아웃</div>
      </Link>
    </>
  );
  return (
    <div className={style.popover}>
      <div className={style.triangle}></div>
      <div className={style.content}>
        {userInfo.userRole === "ROLE_ADMIN" ? adminComponent : userComponent}
      </div>
    </div>
  );
};
