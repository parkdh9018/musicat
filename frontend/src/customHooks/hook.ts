import { $ } from "@/connect/axios/setting";
import { useMutation, useQuery } from "@tanstack/react-query";

interface UserSeq {
  userSeq: number;
}

interface User extends UserSeq {
  useNickname: string;
  userEmail: string;
}

interface BanUser extends User {
  userIsChattingBan: boolean;
}

// 회원 전체 관리(관리자)
export function getAllUsers(page: number) {
  const { data, isLoading } = useQuery(
    ["getAllUsers"],
    async (): Promise<User> => {
      const { data } = await $.get(`/admin/user?page=${page}`);
      return data;
    }
  );

  return { data, isLoading };
}

// 금지 회원 전체 조회 (관리자)

export function getAllBanUsers(page: number) {
  const { data, isLoading } = useQuery(
    ["getAllBanUsers"],
    async (): Promise<BanUser> => {
      const { data } = await $.get(
        `/admin/user/ban?page=${page}&isChattingBan=false&isBan=false`
      );
      return data;
    }
  );

  return { data, isLoading };
}

// 회원 채팅 금지 조치 (관리자)
export function putBanChatting(payload: UserSeq) {
  const { data, isLoading } = useMutation(async (): Promise<BanUser> => {
    const { data } = await $.put(`/admin/user/chattingBan`, payload);
    return data;
  });

  return { data, isLoading };
}

// 회원 활동 금지 조치 (관리자)
export function putBanUser() {
  const { mutate } = useMutation(
    async (payload: UserSeq) => {
      const { data } = await $.put(`/admin/user/ban`, payload);
      return data;
    },
  );

  return { mutate };
}
