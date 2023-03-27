import { userInfoState } from "@/atoms/user.atom";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useRecoilState } from "recoil";
import style from "./Header.module.css";
import { Buffer } from "buffer";
import { OnairSign } from "./onairSign/onairSign";
import { Popover } from "./popover/Popover";
import {
  getUserConfig,
  getUserMoney,
  getUserUnreadMsgNum,
} from "@/connect/axios/queryHooks/user";

export const Header = () => {
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const { isLoading: unreadMsgLoading } = getUserUnreadMsgNum();
  const { isLoading: userMoneyLoading } = getUserMoney();
  const { isLoading: userConfigLoading } = getUserConfig();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const base64Payload = token.split(".")[1];
      const payload = Buffer.from(base64Payload, "base64");
      const result = JSON.parse(payload.toString());
      setUserInfo({
        userSeq: result.sub,
        userRole: result.userRole,
        userProfile: result.userProfileImage,
        userNick: result.userNickname,
      });
    }
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
                <div className={style.badge}>12</div>
              </div>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};
