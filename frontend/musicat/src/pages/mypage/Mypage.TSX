import { memberInfo } from "@/atoms/user.atom";
import { MypageNav } from "@/components/sideNav/mypageNav/MypageNav";
import { Outlet } from "react-router-dom";
import { useRecoilValue } from "recoil";
import style from "./Mypage.module.css";

export const Mypage = () => {
  // <SideNav sideNavTitle={sideNavTitle} sideNavContent={sideNavContent} urls={urls} />
  const userInfo = useRecoilValue(memberInfo);
  const memberTitle = "마이페이지";
  const adminTitle = "관리자페이지";
  const memberContent = [
    "나의 정보 관리",
    "알림 / 공지사항",
    "인벤토리",
    "로그아웃",
  ];
  const adminContent = ["공지사항", "유저관리", "로그아웃"];
  const memberUrls = ["myinfo", "notice", "inventory", ""];
  const adminUrls = ["notice-manage", "user-manage", ""];

  return (
    <div className={style.mypage}>
      <div className={style.leftTab}>
        <MypageNav
          sideNavContent={
            userInfo.memberRole === "admin" ? adminContent : memberContent
          }
          sideNavTitle={
            userInfo.memberRole === "admin" ? adminTitle : memberTitle
          }
          urls={userInfo.memberRole === "admin" ? adminUrls : memberUrls}
        />
      </div>
      <div className={style.rightTab}>
        <div className={style.content}>
          <Outlet />
        </div>
      </div>
    </div>
  );
};
