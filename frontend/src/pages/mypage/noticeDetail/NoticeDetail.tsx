import { nowSideNavState } from "@/atoms/common.atom";
import { userInfoState } from "@/atoms/user.atom";
import { Button } from "@/components/common/button/Button";
import { getAlertDetail } from "@/connect/axios/queryHooks/alert";
import { requestNoticeModify } from "@/connect/axios/queryHooks/notice";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import style from "./NoticeDetail.module.css";

export const NoticeDetail = () => {
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const navigate = useNavigate();
  const userInfo = useRecoilValue(userInfoState);
  const { noticeSeq } = useParams();
  const { data: detail } = getAlertDetail(
    noticeSeq?.charAt(0) === "n"
      ? `/notice/detail?noticeSeq=${noticeSeq.replace("n", "")}`
      : `/alert/detail/${noticeSeq}`
  );

  useEffect(() => {
    if (userInfo.userRole === "ROLE_ADMIN") setNowSideNav("공지사항");
    else setNowSideNav("알림 / 공지사항");
  }, []);

  return (
    <div className={style.notice_detail}>
      <hr className={style.bold_hr} style={{ marginTop: "14px" }} />
      <div className={style.title}>
        <span style={{ textAlign: "center" }}>
          {detail?.alertSeq || detail?.noticeSeq}
        </span>
        <span>{detail?.alertTitle || detail?.noticeTitle}</span>
        <span>{detail?.alertCreatedAt || detail?.noticeCreatedAt}</span>
      </div>
      <hr className={style.thin_hr} style={{ marginTop: "14px" }} />
      <div className={style.content}>
        {detail?.alertContent || detail?.noticeContent}
      </div>
      <hr className={style.thin_hr} />
      {userInfo.userRole === "ROLE_ADMIN" ? (
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
              navigate(-1);
            }}
          />
          <div style={{ display: "inline-block" }}>
            <Button
              content="수정"
              onClick={() => {
                navigate(
                  `/mypage/notice-manage/${noticeSeq?.replace("n", "")}`
                );
              }}
              style={{ marginRight: "5px" }}
            />
            <Button
              content="삭제"
              onClick={() => {
                requestNoticeModify(
                  "delete",
                  navigate,
                  "",
                  "",
                  noticeSeq?.replace("n", "")
                );
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
              navigate(-1);
            }}
          />
        </div>
      )}
    </div>
  );
};
