import { nowSideNav } from "@/atoms/common.atom";
import { memberInfo } from "@/atoms/user.atom";
import { Button } from "@/components/common/button/Button";
import { useEffect } from "react";
import { useRecoilValue, useSetRecoilState } from "recoil";
import style from "./NoticeDetail.module.css";

export const NoticeDetail = () => {
  const setNowSideNav = useSetRecoilState(nowSideNav);
  const userInfo = useRecoilValue(memberInfo);

  useEffect(() => {
    if (userInfo.memberRole === "admin") setNowSideNav("공지사항");
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
      {userInfo.memberRole === "admin" ? (
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            marginTop: "20px",
          }}
        >
          <Button content="목록으로" onClick={() => {}} />
          <div style={{ display: "inline-block" }}>
            <Button
              content="수정"
              onClick={() => {}}
              style={{ marginRight: "10px" }}
            />
            <Button content="삭제" onClick={() => {}} />
          </div>
        </div>
      ) : (
        <div style={{ textAlign: "center", marginTop: "20px" }}>
          <Button content="목록으로" onClick={() => {}} />
        </div>
      )}
    </div>
  );
};
