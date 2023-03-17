import { memberInfo } from "@/atoms/user.atom";
import { useRecoilState } from "recoil";
import style from "./Header.module.css";

export const Header = () => {
  const [userInfo, setUserInfo] = useRecoilState(memberInfo);
  return (
    <header className={style.header}>
      <div className={style.innerHeader}>{userInfo.memberNick}</div>
    </header>
  );
};
