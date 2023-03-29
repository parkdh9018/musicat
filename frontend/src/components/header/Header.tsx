import { userInfoState } from "@/atoms/user.atom";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useRecoilState } from "recoil";
import { OnairSign } from "./onairSign/onairSign";
import { Popover } from "./popover/Popover";
import {
  getUserConfig,
  getUserUnreadMsgNum,
  loginUser,
} from "@/connect/axios/queryHooks/user";
import style from "./Header.module.css";

export const Header = () => {
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const { data: userUnreadMsgNum, isLoading: unreadMsgLoading } =
    getUserUnreadMsgNum();

  //헤더가 아니라 어디선가 받아야 하나?
  const { data: checkDarkMode, isLoading } = getUserConfig();

  useEffect(() => {
    loginUser(setUserInfo);
    document.documentElement.setAttribute("color-theme", "dark");
  }, []);

  useEffect(() => {
    if (!isLoading) {
      if (checkDarkMode?.data.userIsDarkmode)
        document.documentElement.setAttribute("color-theme", "dark");
      else document.documentElement.setAttribute("color-theme", "light");
    }
  }, [checkDarkMode]);

  return (
    <header className={style.header}>
      <div className={style.innerHeader}>
        <div className={style.innerContent} style={{ justifyContent: "start" }}>
          <Link to={"/"}>
            <img className={style.logo_img} src="/img/newlogo.png" alt="로고" />
          </Link>
        </div>
        <div className={style.innerContent}>
          <OnairSign />
        </div>
        <div className={style.innerContent} style={{ justifyContent: "end" }}>
          {!userInfo.userRole ? (
            <div className={style.user_info}>
              <span
                className={style.nickname_other}
                onClick={() => {
                  window.open(
                    "https://musicat.kr/api/oauth2/authorization/kakao",
                    "musicat-kakao-login",
                    "width=500,height=500"
                  );
                }}
              >
                로그인
              </span>
            </div>
          ) : (
            <div className={style.user_info}>
              <span className={style.nickname}>{userInfo.userNick}</span>
              <span className={style.nickname_other}>님 환영합니다</span>
              <div className={style.popover_state}>
                <Popover />
              </div>
              <div className={style.profile_div}>
                <img src={userInfo.userProfile} alt="프로필 이미지" />
                {userUnreadMsgNum?.data.userUnreadMessage ? (
                  <div className={style.badge}>
                    {userUnreadMsgNum?.data.userUnreadMessage}
                  </div>
                ) : null}
              </div>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};
