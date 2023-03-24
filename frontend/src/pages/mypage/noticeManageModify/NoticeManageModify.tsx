import { nowSideNavState } from "@/atoms/common.atom";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import style from "./NoticeManageModify.module.css";
import {
  getNoticeDetail,
  requestNoticeModify,
} from "@/connect/axios/queryHooks/notice";

export const NoticeManageModify = () => {
  const { noticeSeq } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const setNowSideNav = useSetRecoilState(nowSideNavState);

  // param이 new라면 새로운 글 작성이니까 새글작성 세팅
  const { data: detail } = getNoticeDetail(
    `/notice/detail?noticeSeq=${noticeSeq}`,
    noticeSeq,
    setTitle,
    setContent
  );

  useEffect(() => {
    setNowSideNav("공지사항");
  }, []);

  return (
    <div className={style.NoticeManageModify}>
      <div>
        <span>제목 :</span>
        <Input input={title} setInput={setTitle} style={{ width: "80%" }} />
      </div>
      <div className={style.content_div}>
        <span>내용 :</span>
        <textarea
          className={style.content}
          value={content}
          onChange={(e) => {
            setContent(e.target.value);
          }}
        />
        <div style={{ clear: "both" }} />
      </div>
      <div className={style.button_box}>
        <Button
          content="취 소"
          onClick={() => {
            navigate(-1);
          }}
          style={{ marginRight: "7px" }}
        />
        <Button
          content={noticeSeq === "new" ? "등 록" : "수 정"}
          onClick={() => {
            noticeSeq === "new"
              ? requestNoticeModify("post", navigate, title, content)
              : requestNoticeModify(
                  "patch",
                  navigate,
                  title,
                  content,
                  noticeSeq
                );
          }}
        />
      </div>
    </div>
  );
};
