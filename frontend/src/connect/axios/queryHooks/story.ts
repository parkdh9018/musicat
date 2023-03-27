import { $ } from "@/connect/axios/setting";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { StoryRequest } from "@/types/home";
import { useQuery } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { toast } from "react-toastify";


// 사연 신청
// TODO : 이거 react query로 바꿔야 할듯
export function postRequestStory(userSeq:number, payload : StoryRequest) {
    
    if(!userSeq) {
        useCustomToast("error","로그인이 필요합니다");
    } else if(!payload.storyTitle) {
        useCustomToast("error","제목을 입력해주세요");
        return;
    } else if (payload.storyContent.length == 0 || payload.storyContent[0].content == "") {
        useCustomToast("error","내용을 확인해주세요");
        return
    } else if (payload.storyMusicTitle == "") {
        useCustomToast("error","신청곡을 확인해주세요");
        return;
    }
    $.post("/story", payload).then(() => {
        useCustomToast("success", "사연이 신청되었습니다");
    }).catch((e) => {
        useCustomToast("error", e.reponse.data);
    });
}

export function isAlreadyReqeustStory(seq: number) {
    $.get(`/story/unique/${seq}`).then((data) => {
        // console.log(data)
    }).catch((e:AxiosError) => {
        useCustomToast("error", e.response?.data as string);
    })
}
