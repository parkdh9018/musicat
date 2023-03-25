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
export function getUsers() {
  const [searchInput, setSearchInput] = useState<string>("");
  const [searchSelectValue, setSearchSelectValue] = useState<string>("all");

  const { data, isLoading, refetch } = useQuery(
    ["getAllUsers"],
    async (): Promise<PagableResponse<User>> => {
      let isChattingBan: boolean | null = null;
      if (searchSelectValue === "banChat") {
        isChattingBan = true;
      } else if (searchSelectValue === "notBanChat") {
        isChattingBan = false;
      }

      let isBan: boolean | null = null;
      if (searchSelectValue === "ban") {
        isBan = true;
      } else if (searchSelectValue === "notBan") {
        isBan = false;
      }

      const { data } = await $.get(
        `/admin/user?page=0&userNickname=${searchInput}&isChattingBan=${isChattingBan}&isBan=${"isBan"}`
      );
      return data;
    }
  );
  const userList = data?.content;
  return {
    isLoading,
    userList,
    refetch,
    searchInput,
    setSearchInput,
    searchSelectValue,
    setSearchSelectValue,
  };
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
  const { mutate } = useMutation(async (payload: UserSeq) => {
    const { data } = await $.put(`/admin/user/ban`, payload);
    return data;
  });

  return { mutate };
}
