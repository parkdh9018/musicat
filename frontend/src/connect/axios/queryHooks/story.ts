
import { $ } from "@/connect/axios/setting";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { StoryRequest } from "@/types/home";
import { useMutation, useQuery } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { useState } from "react";

export function storyHook(userSeq: number) {
  const [button, setButton] = useState(false);

  // 현재 사연 신청 가능한지 체크
  const { data } = useQuery(
    ["storyRequest", userSeq],
    async ({ queryKey }): Promise<string> => {
      const { data } = await $.get(`/story/unique/${queryKey[1]}`);
      return data;
    },
    {
      onError: (e: AxiosError) => {
        useCustomToast("error", e.response?.data as string);
        setButton(false);
      },
      enabled: button,
    }
  );

  // 사연 신청
  const { mutate, isLoading } = useMutation(
    async (payload: StoryRequest): Promise<boolean> => {
      if (!payload.storyTitle) {
        useCustomToast("error", "제목을 입력해주세요");
      } else if (
        payload.storyContent.length == 0 ||
        payload.storyContent.some(v => v.content == "")
      ) {
        useCustomToast("error", "내용을 확인해주세요");
      } else if (payload.storySong.musicTitle == "") {
        useCustomToast("error", "신청곡을 확인해주세요");
      } else {
        console.log(payload);
        // await $.put("/story", payload);
        return true;
      }

      return false;
    },
    {
      onSuccess: (data) => {
        if (data) {
          useCustomToast("success", "사연이 신청되었습니다");
          setButton(false);
        }
      },
      onError: (e: AxiosError) => {
        useCustomToast("error", e.response?.data as string);
      },
    }
  );

  return { mutate, data, button, setButton, isLoading };
}