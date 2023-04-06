import { nowMainPageState } from "@/atoms/common.atom";
import { userInfoState } from "@/atoms/user.atom";
import { MypageNav } from "@/components/sideNav/mypageNav/MypageNav";
import { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import style from "./Mypage.module.css";
import Swal from "sweetalert2";
import { useTokenData } from "@/customHooks/useTokenData";

export const Mypage = () => {
  const userInfo = useRecoilValue(userInfoState);
  const userToken = useTokenData();
  const setNowMainPage = useSetRecoilState(nowMainPageState);
  const navigate = useNavigate();

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

  useEffect(() => {
    setNowMainPage(false);
    if (!userToken?.userNick) {
      Swal.fire({
        icon: "warning",
        title: "",
        text: "로그인이 필요한 서비스 입니다!",
        confirmButtonText: "닫기",
      }).then(() => {
        navigate("/");
      });
    }
  }, []);

  return (
    <>
      <div>
        <div className={style.mypage}>
          <div className={style.leftTab}>
            <MypageNav
              sideNavContent={
                userInfo.userRole === "ROLE_ADMIN"
                  ? adminContent
                  : memberContent
              }
              sideNavTitle={
                userInfo.userRole === "ROLE_ADMIN" ? adminTitle : memberTitle
              }
              urls={userInfo.userRole === "ROLE_ADMIN" ? adminUrls : memberUrls}
            />
          </div>
          <div className={style.rightTab}>
            <div className={style.content}>
              <Outlet />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
