import { $ } from "@/connect/axios/setting";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { StoryRequest } from "@/types/home";
import { useQuery } from "@tanstack/react-query";
import { toast } from "react-toastify";


// 사연 신청
export function postRequestStory(payload : StoryRequest) {
    
    if(!payload.storyTitle) {
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

export function isAlreadyReqeustStory(seq: string) {
    $.get(`/story/unique/${seq}`).then(

    )
}
