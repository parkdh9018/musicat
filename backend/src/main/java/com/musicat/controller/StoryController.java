package com.musicat.controller;

import com.google.gson.Gson;
import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.dto.YoutubeSearchResultDto;
import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.service.SpotifyApiService;
import com.musicat.service.StoryService;

import com.musicat.service.YoutubeApiService;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/story")
@RequiredArgsConstructor
public class StoryController {

    // logger 정의
    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);

    // Service 정의
    private final StoryService storyService;

    /**
     * 사연 신청
     *
     * @param map
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> insertStory(/**@RequestBody StoryRequestDto storyRequestDto**/
            @RequestBody
            Map<String, Object> map) {

        // spotify, youtube 검색 결과
        HashMap<String, String> storySongMap = (HashMap<String, String>) map.get("storySong");

        Gson gson = new Gson();
        // Object -> Json 타입 변환
        String jsonStoryContent = gson.toJson(map.get("storyContent"));

        // Todo : JWT에서 userSeq 꺼내는 로직으로 변경해야함
        long longUserSeq = Long.parseLong(String.valueOf(map.get("userSeq")));
        long musicLength = Long.parseLong(String.valueOf(storySongMap.get("musicLength")));


        StoryRequestDto storyRequestDto = StoryRequestDto.builder()
                .userSeq(longUserSeq)
                .storyTitle((String) map.get("storyTitle"))
                .storyContent(jsonStoryContent)
                .storyMusicTitle(storySongMap.get("musicTitle"))
                .storyMusicArtist(storySongMap.get("musicArtist"))
                .storyMusicCover(storySongMap.get("musicImage"))
                .videoId(storySongMap.get("videoId"))
                .musicLength(musicLength)
                .build();

        // 이미 신청한 사연이 있는지 검증
        storyService.isUniqueStory(longUserSeq);

        // 사연 등록 호출
        storyService.insertStory(storyRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    /**
//     * 사연 1개 조회 (읽을 사연) (가장 먼저 신청한 사연 중 1개 조회)
//     */
//    @GetMapping("/story")
//    public ResponseEntity<?> getTopStory() {
//        try {
//            Object result = storyService.getTopStoryInfo();
//
//            if (result == null) return ResponseEntity.noContent().build(); // 사연 검색 결과 없음
//
//            return ResponseEntity.ok(result); // 사연 검색 성공. (검색한 사연 정보 반환)
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    /**
     * 사연 중복 검사 (신청 사연 리스트에 memberSeq로 신청한 사연이 있는지 여부 true  : 중복 있음 (사연 신청 불가능) false : 중복 없음 (사연 신청
     * 가능)
     */
    @GetMapping("/unique/{userSeq}")
    public ResponseEntity<?> isUniqueStory(@PathVariable long userSeq) {
        storyService.isUniqueStory(userSeq);
        return ResponseEntity.ok().build();

    }

    /**
     * 사연 상세 조회
     *
     * @param storySeq
     * @return
     */
    @GetMapping("/{storySeq}")
    public ResponseEntity<?> getStory(@PathVariable long storySeq) {
        StoryInfoDto storyInfoDto = storyService.getStory(storySeq);

        return ResponseEntity.ok(storyInfoDto);
    }

    /**
     * 사연 삭제
     * @param storySeq
     * @return
     */
    @DeleteMapping("/{storySeq}")
    public ResponseEntity<Void> deleteStory(@PathVariable long storySeq) {
        storyService.deleteStory(storySeq);

        return ResponseEntity.ok().build();
    }


}
