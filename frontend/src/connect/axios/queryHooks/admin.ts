import { $ } from "@/connect/axios/setting";
import { useMutation, useQuery } from "@tanstack/react-query";
import { PagableResponse } from "@/types/mypage";

interface UserSeq {
  userSeq: number;
}

interface User extends UserSeq {
  userCreatedAt: string;
  userEmail: string;
  userIsBan: boolean;
  userIsChattingBan: boolean;
  userIsUser: boolean;
  useNickname: string;
}


// 회원 전체 관리(관리자)
export function getAllUsers(page: number) {
  const { data, isLoading } = useQuery(
    ["getAllUsers"],
    async (): Promise<PagableResponse<User>> => {
      const { data } = await $.get(`/admin/user?page=${page}`);
      return data;
    }
  );
  const userList = data?.content;
  return { isLoading, userList };
}

// 금지 회원 전체 조회 (관리자)

export function getAllBanUsers(page: number, isChattingBan?:boolean, isBan?:boolean) {

  const url = `/admin/user/ban?page=${page}` 
  + isChattingBan == undefined ? `` : `&isChattingBan=${isChattingBan}`
  + isBan == undefined ? `` : `&isChattingBan=${isBan}`;
  
  const { data, isLoading } = useQuery(
    ["getAllBanUsers"],
    async (): Promise<PagableResponse<User>> => {
      const { data } = await $.get(url);
      return data;
    },
  );

  const userList = data?.content;
  return { userList, isLoading };
}

// TODO : 이거 토글 말고 정지는 정지, 해제는 해제로 바꿔야 함
// 회원 채팅 금지 조치 (관리자) 
export function putBanChatting(payload: UserSeq) {
  const { data, isLoading } = useMutation(async (): Promise<User> => {
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
