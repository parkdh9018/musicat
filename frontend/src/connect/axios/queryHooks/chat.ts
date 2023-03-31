import { useQuery } from "@tanstack/react-query";
import { $ } from "../setting";

// 채팅 인원 소환
export function getPeopleCnt() {
  const { data, isLoading } = useQuery(
    ["getPeopleCnt"],
    async (): Promise<number> => {
      const { data } = await $.get("/chat/count");
      return data;
    },
    { refetchInterval: 5000 }
  );

  return { data, isLoading };
}
