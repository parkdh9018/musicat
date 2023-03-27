import { storyContentState, storyTitleState } from "@/atoms/story.atoms";
import { $ } from "@/connect/axios/setting";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { StoryRequest } from "@/types/home";
import { AxiosError } from "axios";
import { useResetRecoilState } from "recoil";


const recoilReset = () => {
    useResetRecoilState(storyTitleState);
    useResetRecoilState(storyContentState);
    //TODO : 노래 수정 필요
    // useResetRecoilState(songTitleState);
}
// 사연 신청
export function postRequestStory(userSeq:number, payload : StoryRequest, setButton:React.Dispatch<React.SetStateAction<boolean>>) {
    
    if(!userSeq) {
        useCustomToast("error","로그인이 필요합니다");
    } else if(!payload.storyTitle) {
        useCustomToast("error","제목을 입력해주세요");
    } else if (payload.storyContent.length == 0 || payload.storyContent[0].content == "") {
        useCustomToast("error","내용을 확인해주세요");
    } else if (payload.storyMusicTitle == "") {
        useCustomToast("error","신청곡을 확인해주세요");
    } else {
        $.post("/story", payload).then(() => {
            useCustomToast("success", "사연이 신청되었습니다");
            setButton(false);
            recoilReset();
        }).catch((e) => {
            useCustomToast("error", e.reponse.data);
        });
    }

}

export function isAlreadyReqeustStory(userSeq: number, setButton:React.Dispatch<React.SetStateAction<boolean>>) {

    if(!userSeq) {
        useCustomToast("error","로그인이 필요합니다");
        setButton(false);

    } else {
        $.get(`/story/unique/${userSeq}`).then((data) => {
            // console.log(data)
            setButton(false);
        }).catch((e:AxiosError) => {
            useCustomToast("error", e.response?.data as string);
        })
    }
}
