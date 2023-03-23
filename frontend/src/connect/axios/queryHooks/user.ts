import { $ } from "@/connect/axios/setting";
import { useMutation, useQuery } from "@tanstack/react-query";

interface UserSeq {
  userSeq: number;
}

interface ModifyRequest extends UserSeq {
  userNickname: string;
}

// 회원 정보 수정
export function putModifyUser(payload: ModifyRequest) {
  const { data, isLoading } = useMutation(["putModifyUser"], async () => {
    const { data } = await $.put(`/user/nickname`, payload);
    return data;
  });

  return { data, isLoading };
}

// 회원 탈퇴
export function deleteUser(userSeq: string) {
  const { data, isLoading } = useMutation(["deleteUser"], async () => {
    return await $.delete(`/user?userSeq=${userSeq}`);
  });

  return { data, isLoading };
}

// 회원 재화 내역 상세 조회
export function getUserMoneyDetail(moneyLogSeq: string) {
  const { data, isLoading } = useQuery(["getUserMoneyDetail"], async () => {
    return await $.get(`/user/money?moneyLogSeq=${moneyLogSeq}`);
  });

  return { data, isLoading };
}
