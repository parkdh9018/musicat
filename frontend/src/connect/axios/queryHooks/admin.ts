import { $ } from "@/connect/axios/setting";
import { useMutation, useQuery } from "@tanstack/react-query";
import { PagableResponse } from "@/types/mypage";
import { useEffect, useMemo, useState } from "react";

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

  let isChattingBan: boolean | null = null;
  let isBan: boolean | null = null;

  useEffect(() => {
    if (searchSelectValue === "banChat") {
      isChattingBan = true;
    } else if (searchSelectValue === "notBanChat") {
      isChattingBan = false;
    }

    if (searchSelectValue === "ban") {
      isBan = true;
    } else if (searchSelectValue === "notBan") {
      isBan = false;
    }
  }, [searchSelectValue]);

  const { data, isLoading, refetch } = useQuery(
    ["getAllUsers"],
    async (): Promise<PagableResponse<User>> => {
      const { data } = await $.get(
        `/admin/user?page=0&userNickname=${searchInput}&isChattingBan=${isChattingBan}&isBan=${isBan}`
      );
      return data;
    }
  );

  const banMutation = (url: string) => {
    const { mutate } = useMutation(
      async (): Promise<void> => {
        const { data } = await $.put(
          url,
          userList?.map((v) => {
            v.userSeq;
          })
        );
        return data;
      },
      {
        onSuccess: () => {
          refetch();
        },
      }
    );

    return mutate;
  };

  const chattingBanMutate = banMutation(`/admin/user/chatting-ban`);
  const chattingNotBanMutate = banMutation(`/admin/user/not-chatting-ban`);
  const BanMutate = banMutation(`/admin/user/ban`);
  const NotBanMutate = banMutation(`/admin/user/not-ban`);

  const userList = useMemo(() => data?.content, [data]);

  return {
    isLoading,
    userList,
    searchInput,
    searchSelectValue,
    refetch,
    setSearchInput,
    setSearchSelectValue,
    chattingBanMutate,
    chattingNotBanMutate,
    BanMutate,
    NotBanMutate,
  };
}
