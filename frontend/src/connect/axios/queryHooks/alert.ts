import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { PagableResponse } from "@/types/mypage";
import { $ } from "../setting";

interface Alert {
  alertSeq: number;
  // 이거 필요없음
  userSeq: number;
  alertTitle: string;
  // 이거 필요없음
  alertContent: string;
  alertCreatedAt: string;
  alertIsRead: boolean;
}

// 알람 list를 받아오기
export function getAlertList(pageNum: number, search: string | null) {
  async function fetchAlertList(): Promise<PagableResponse<Alert>> {
    const { data } = await $.get(
      `/alert/1?page=${pageNum ? pageNum - 1 : ""}&query=${
        search ? search : ""
      }`
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
  async function fetchAlertDetail(): Promise<any> {
    const { data } = await $.get(url);
    return data;
  }
  const { data, isLoading } = useQuery(["AlertDetail" + url], fetchAlertDetail);
  return { data, isLoading };
}

// 알람을 모두 읽었다는  api 요청
export function patchReadAllAlerts(userSeq: number) {
  $.patch(`/alert/unread/${userSeq}`, { alertIsRead: true });
}

/** 
// 알람을 읽었다고 optimistic update
export function temp(pageNum: any, alertSeq: any) {
  const queryClient = useQueryClient();

  async function fetchAlert() {
    const { data } = await $.patch(`/alert`, {
      alertSeq: alertSeq,
      alertRead: true,
    });
    return data;
  }
  const { mutate: readAlert } = useMutation(fetchAlert, {
    onMutate: async () => {
      await queryClient.cancelQueries(["AlertListUser", pageNum]);

      const oldData: Alert[] | undefined = queryClient.getQueryData([
        "AlertListUser",
        pageNum,
      ]);

      queryClient.setQueryData(["AlertListUser", pageNum], (oldData: any) => {
        const newData = [...oldData];

        newData.forEach((content) => {
          if (content.alertSeq === alertSeq) {
            content.alertIsRead = true;
            return;
          }
        });

        return newData;
      });
      return { oldData };
    },
    onError: (_error, _variables, context) => {
      queryClient.setQueryData(["AlertListUser", pageNum], context?.oldData);
    },
    onSettled: () => {
      queryClient.invalidateQueries(["AlertListUser", pageNum]);
    },
  });

  return readAlert;
}
 */
