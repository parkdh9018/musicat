import { $ } from "@/connect/axios/setting";
import { PagableResponse } from "@/types/mypage";
import { useQuery } from "@tanstack/react-query";
import { NavigateFunction } from "react-router-dom";
import Swal from "sweetalert2";

interface Notice {
  noticeSeq: number;
  noticeCreatedAt: string;
  noticeTitle: string;
  // 이거 필요없음
  userNickname: string;
}

export interface NoticeDetail extends Notice {
  noticeUpdatedAt: string;
  noticeContent: string;
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
      noticeTitle: title,
      noticeContent: content,
    }).then(() =>
      Swal.fire({
        icon: "success",
        title: "",
        text: "작성되었습니다.",
        confirmButtonText: "닫기",
      }).then(() => {
        navigate(-1);
      })
    );
  else if (type === "patch")
    $.patch("/admin/notice", {
      noticeSeq: noticeSeq,
      noticeTitle: title,
      noticeContent: content,
    }).then(() =>
      Swal.fire({
        icon: "success",
        title: "",
        text: "수정되었습니다.",
        confirmButtonText: "닫기",
      }).then(() => {
        navigate(-1);
      })
    );
  else
    Swal.fire({
      icon: "warning",
      title: "",
      text: "삭제하시겠습니까?",

      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "확인",
      cancelButtonText: "취소",
    }).then((result) => {
      if (result.isConfirmed) {
        $.delete(`/admin/notice/${noticeSeq}`).then(() => navigate(-1));
      }
    });
}

// 공지사항 detail를 받아오기
export function getNoticeDetail(
  url: string,
  noticeSeq?: string,
  setTitle?: React.Dispatch<React.SetStateAction<string>>,
  setContent?: React.Dispatch<React.SetStateAction<string>>
) {
  async function fetchNoticeDetail(): Promise<NoticeDetail> {
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
