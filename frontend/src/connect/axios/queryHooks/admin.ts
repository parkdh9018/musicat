import { $ } from "@/connect/axios/setting";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/react-query";
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

  const { data, isLoading, refetch, fetchNextPage, isFetchingNextPage } =
    useInfiniteQuery(
      ["getAllUsers"],
      async ({ pageParam = 0 }): Promise<PagableResponse<User>> => {
        const { data } = await $.get(
          `/admin/user?page=${pageParam}&userNickname=${searchInput}&isChattingBan=${isChattingBan}&isBan=${isBan}`
        );
        return data;
      },
      {
        getNextPageParam: ({ number, last }) => {
          if (!last) return number + 1;
        },
      }
    );

  const banMutation = (url: string, filterd_list: User[]) => {
    const { mutate } = useMutation(
      async (): Promise<void> => {
        const { data } = await $.put(
          url,
          filterd_list?.map((v) => {
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

  const chattingBanMutate = (filterd_list: User[]) => banMutation(`/admin/user/chatting-ban`, filterd_list);
  const chattingNotBanMutate = (filterd_list: User[]) => banMutation(`/admin/user/not-chatting-ban`, filterd_list);
  const BanMutate = (filterd_list: User[]) => banMutation(`/admin/user/ban`, filterd_list);
  const NotBanMutate = (filterd_list: User[]) => banMutation(`/admin/user/not-ban`, filterd_list);

  const userList = useMemo(() => {
    const result: User[] = [];
    data?.pages.forEach((page) => {
      page.content.forEach((item) => {
        result.push(item);
      });
    });

    return result;
  }, [data]);

  return {
    isLoading,
    userList,
    searchInput,
    searchSelectValue,
    isFetchingNextPage,
    refetch,
    setSearchInput,
    setSearchSelectValue,
    chattingBanMutate,
    chattingNotBanMutate,
    BanMutate,
    NotBanMutate,
    fetchNextPage,
  };
}
