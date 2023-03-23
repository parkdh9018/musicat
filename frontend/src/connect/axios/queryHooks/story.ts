import { $ } from "@/connect/axios/setting";
import { useQuery } from "@tanstack/react-query";


interface StoryContent {
    seq : number;
    speaker : string;
    content : string;
}
interface StoryRequest {    
    userSeq : number;
    storyTitle : string;
    storyContents : StoryContent[];
    storyMusicName : string;
    storyMusicArtist : string;
}

// 사연 신청
export function postRequestStory(payload : StoryRequest) {
    $.post("/story", payload);
}

// 사연 1개 조회
export function getStoryDetail() {

}

// 사연 상세 조회
// export function get