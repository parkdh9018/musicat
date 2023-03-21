import { userInfoState } from "@/atoms/user.atom";
import { useRecoilState } from "recoil";
import style from "./Header.module.css";
import { OnairSign } from "./onairSign/onairSign";
import { Popover } from "./popover/Popover";


export const Header = () => {
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  return (
    <header className={style.header}>
      <div className={style.innerHeader}>
        <img className={style.logo_img} src="/img/logo.png" alt="로고"/>
        <div style={{flexGrow:"1", textAlign:"center"}}>
          <OnairSign/>
        </div>
        <div className={style.user_info}>
          <span className={style.nickname}>
            {userInfo.userNick}
          </span>
          <span className={style.nickname_other}>
            님 환영합니다
          </span>
          <div className={style.popover_state}>
            <Popover/>
          </div>
          <img src={userInfo.userProfile} alt="프로필 이미지"/>
        </div>

      </div>
    </header>
  );
};
