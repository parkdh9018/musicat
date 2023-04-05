import { $ } from "@/connect/axios/setting";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { StoryRequest } from "@/types/home";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";

export function storyHook(userSeq: number) {
  // 현재 사연 신청 가능한지 체크
  const { data } = useQuery(
    ["storyRequest", userSeq],
    async (): Promise<string> => {
      const request = await $.get("/story/unique")
        .then((res) => res.status)
        .catch((e) => e.response.status);

      return request;
    },
    {
      retry: false,
    }
  );

  // 사연 신청
  const {
    mutate,
    isLoading,
    data: storyReqeustData,
  } = useMutation(
    async (payload: StoryRequest): Promise<boolean> => {
      if (!payload.storyTitle) {
        useCustomToast("error", "제목을 입력해주세요");
      } else if (
        payload.storyContent.length == 0 ||
        payload.storyContent.some((v) => v.content == "")
      ) {
        useCustomToast("error", "내용을 확인해주세요");
      } else if (payload.storySong.musicTitle == "") {
        useCustomToast("error", "신청곡을 확인해주세요");
      } else {
        await $.post("/story", payload).catch(() => {
          useCustomToast("error", "사연을 신청하지 못하였습니다");
        });
        return true;
      }

      return false;
    },
    {
      onSuccess: (data) => {
        if (data) {
          useCustomToast("success", "사연이 신청되었습니다");
          return true;
        }
      },
    }
  );

  return { mutate, data, storyReqeustData, isLoading };
}
