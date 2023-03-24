import { $ } from "@/connect/axios/setting";
import { useMutation, useQuery } from "@tanstack/react-query";
import { PagableResponse } from "@/types/mypage";
import { useState } from "react";


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
  const [userList, setUserList] = useState<User[] | undefined>([]);

  // console.log(data?.content)
  // data?.content ? setUserList((prev) => [...prev, data?.content]) : undefined;

  return { isLoading, userList, setUserList };
}

// 금지 회원 전체 조회 (관리자)

export function getAllBanUsers(page: number, isChattingBan = false, isBan = false) {
  const { data, isLoading } = useQuery(
    ["getAllBanUsers"],
    async (): Promise<PagableResponse<User>> => {
      const { data } = await $.get(
        `/admin/user/ban?page=${page}&isChattingBan=${isChattingBan}&isBan=${isBan}`
      );
      return data;
    }
  );
  
  const [userList, setUserList] = useState<User[] | undefined>([]);

  return { isLoading, userList, setUserList };
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
