import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { PagableResponse } from "@/types/mypage";
import { $ } from "../setting";
import { NoticeDetail } from "./notice";

interface Alert {
  alertSeq: number;
  alertTitle: string;
  alertCreatedAt: string;
  alertIsRead: boolean;
}

interface AlertDetail extends Alert {
  alertContent: string;
}

interface NoticeAlertDetail extends AlertDetail, NoticeDetail {}

// 알람 list를 받아오기
export function getAlertList(pageNum: number, search: string | null) {
  async function fetchAlertList(): Promise<PagableResponse<Alert>> {
    const { data } = await $.get(
      `/alert?page=${pageNum ? pageNum - 1 : ""}&query=${search ? search : ""}`
    );
    return data;
  }
  const { data, isLoading, refetch } = useQuery(
    ["AlertListUser" + search, pageNum],
    fetchAlertList
  );
  return { data, isLoading, refetch };
}

// 알람/공지사항 detail를 받아오기 => 분류가 애매함
export function getAlertDetail(url: string) {
  async function fetchAlertDetail(): Promise<NoticeAlertDetail> {
    const { data } = await $.get(url);
    return data;
  }
  const { data, isLoading } = useQuery(["AlertDetail" + url], fetchAlertDetail);
  return { data, isLoading };
}

// 알람을 모두 읽었다고 optimistic updates
export function patchReadAllAlerts(search: string, pageNum: number) {
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    async () => await $.patch(`/alert/unread`, { alertIsRead: true }),
    {
      onMutate: async () => {
        await queryClient.cancelQueries(["AlertListUser" + search, pageNum]);

        const oldData: Alert[] | undefined = queryClient.getQueryData([
          "AlertListUser" + search,
          pageNum,
        ]);

        queryClient.setQueryData(
          ["AlertListUser" + search, pageNum],
          (oldData: any) => {
            const newData = { ...oldData };

            newData.content.forEach((el: Alert) => {
              el.alertIsRead = true;
            });

            return newData;
          }
        );
        return { oldData };
      },
      onError: (_error, _variables, context) => {
        queryClient.setQueryData(
          ["AlertListUser" + search, pageNum],
          context?.oldData
        );
      },
      onSettled: () => {
        queryClient.invalidateQueries(["AlertListUser" + search, pageNum]);
      },
    }
  );

  return { mutate };
}
