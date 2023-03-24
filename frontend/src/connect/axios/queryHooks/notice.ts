import { $ } from "@/connect/axios/setting";
import { useQuery } from "@tanstack/react-query";

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
