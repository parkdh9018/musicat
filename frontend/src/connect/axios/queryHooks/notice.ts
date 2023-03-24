import { $ } from "@/connect/axios/setting";
import { PagableResponse } from "@/types/mypage";
import { useQuery } from "@tanstack/react-query";
import { NavigateFunction } from "react-router-dom";

interface Notice {
  noticeSeq: number;
  noticeCreatedAt: string;
  noticeTitle: string;
  // 이거 필요없음
  userNickname: string;
}

// 공지사항 상위 3개를 가져오기
export function getTop3Notice() {
  async function fetchTop3Notice(): Promise<Notice[]> {
    const { data } = await $.get("/notice/top3");
    return data;
  }
  const { data, isLoading } = useQuery(["Top3Notice"], fetchTop3Notice);
  return { data, isLoading };
}

// 공지사항 list를 받아오기
export function getNoticeList(pageNum: number, search: string | null) {
  async function fetchNoticeList(): Promise<PagableResponse<Notice>> {
    const { data } = await $.get(
      `/notice?page=${pageNum ? pageNum - 1 : ""}&query=${search ? search : ""}`
    );
    return data;
  }
  const { data, isLoading, refetch } = useQuery(
    ["NoticeList" + search, pageNum],
    fetchNoticeList
  );
  return { data, isLoading, refetch };
}

// 공지사항 등록/수정/삭제 하기
export function requestNoticeModify(
  type: string,
  navigate: NavigateFunction,
  title?: string,
  content?: string,
  noticeSeq?: string
) {
  if (type === "post")
    $.post("/admin/notice", {
      userSeq: 1,
      noticeTitle: title,
      noticeContent: content,
    }).then(() => navigate(-1));
  else if (type === "patch")
    $.patch("/admin/notice", {
      noticeSeq: noticeSeq,
      noticeTitle: title,
      noticeContent: content,
    }).then(() => navigate(-1));
  else $.delete(`/admin/notice/${noticeSeq}`).then(() => navigate(-1));
}

// 공지사항 detail를 받아오기
export function getNoticeDetail(
  url: string,
  noticeSeq?: string,
  setTitle?: React.Dispatch<React.SetStateAction<string>>,
  setContent?: React.Dispatch<React.SetStateAction<string>>
) {
  async function fetchNoticeDetail(): Promise<any> {
    const { data } = await $.get(url);
    return data;
  }
  const { data, isLoading } = useQuery(
    ["AlertDetail" + url],
    fetchNoticeDetail,
    {
      enabled: noticeSeq !== "new",
      onSuccess: (data) => {
        setTitle && setTitle(data?.noticeTitle);
        setContent && setContent(data?.noticeContent);
      },
    }
  );
  return { data, isLoading };
}
