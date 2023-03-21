import { nowSideNav } from "@/atoms/common.atom";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useSetRecoilState } from "recoil";

export const NoticeManageModify = () => {
  const { noticeSeq } = useParams();
  const setNowSideNav = useSetRecoilState(nowSideNav);

  // 리엑트 쿼리를 사용해서 detail 정보를 가져온다.
  // param이 new라면 새로운 글 작성이니까 새글작성 세팅

  useEffect(() => {
    setNowSideNav("공지사항");
  }, []);

  return <div>NoticeManageModify</div>;
};
