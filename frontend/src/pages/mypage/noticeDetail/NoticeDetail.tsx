import { nowSideNavState } from "@/atoms/common.atom";
import { userInfoState } from "@/atoms/user.atom";
import { Button } from "@/components/common/button/Button";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import style from "./NoticeDetail.module.css";

export const NoticeDetail = () => {
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const navigate = useNavigate();
  const userInfo = useRecoilValue(userInfoState);

  useEffect(() => {
    if (userInfo.userRole === "admin") setNowSideNav("공지사항");
    else setNowSideNav("알림 / 공지사항");
  }, []);
  return (
    <div className={style.notice_detail}>
      <hr className={style.bold_hr} style={{ marginTop: "14px" }} />
      <div className={style.title}>
        <span style={{ textAlign: "center" }}>1</span>
        <span>사연 당첨을 축하합니다!</span>
        <span>2022년 12월 14일(수) 13:21</span>
      </div>
      <hr className={style.thin_hr} style={{ marginTop: "14px" }} />
      <div className={style.content}>내용?</div>
      <hr className={style.thin_hr} />
      {userInfo.userRole === "admin" ? (
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            marginTop: "20px",
          }}
        >
          <Button
            content="목록으로"
            onClick={() => {
              navigate("/mypage/notice");
            }}
          />
          <div style={{ display: "inline-block" }}>
            <Button
              content="수정"
              onClick={() => {
                navigate(`/mypage/notice-manage/쿼리에서-받아온정보`);
              }}
              style={{ marginRight: "10px" }}
            />
            <Button
              content="삭제"
              onClick={() => {
                return;
              }}
            />
          </div>
        </div>
      ) : (
        <div style={{ textAlign: "center", marginTop: "20px" }}>
          <Button
            content="목록으로"
            onClick={() => {
              return;
            }}
          />
        </div>
      )}
    </div>
  );
};
