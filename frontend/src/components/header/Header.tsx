import { userInfoState } from "@/atoms/user.atom";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useRecoilState, useSetRecoilState } from "recoil";
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
  const { isLoading: userConfigLoading } = getUserConfig();

  // 나중에 다시 확인
  document.addEventListener("visibilitychange", function () {
    if (document.visibilityState === "visible") {
      console.log("감지를 했다!!!!");
      loginUser(setUserInfo);
    } else {
      console.log("focous 벗어남");
    }
  });

  useEffect(() => {
    loginUser(setUserInfo);
  }, []);

  return (
    <header className={style.header}>
      <div className={style.innerHeader}>
        <div className={style.innerContent} style={{ justifyContent: "start" }}>
          <Link to={"/"}>
            <img className={style.logo_img} src="/img/logo.png" alt="로고" />
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
