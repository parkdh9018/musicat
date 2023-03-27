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


    // 테스트1
    private final YoutubeApiService youtubeApiService;
    private final SpotifyApiService spotifyApiService;

    @GetMapping("/test/youtube")
    public ResponseEntity<?> testYoutube() {
        YoutubeSearchResultDto youtubeSearchResultDto = youtubeApiService.findVideo("디토", "뉴진스");

        String videoId = youtubeSearchResultDto.getVideoId();
        long musicLength = youtubeSearchResultDto.getMusicLength();

        logger.debug("videoId : {}, musicLength : {}", videoId, musicLength);

        return ResponseEntity.ok(videoId);
    }


    // 테스트2
    @GetMapping("/test/spotify")
    public ResponseEntity<?> testSpotify() throws IOException {
        List<SpotifySearchResultDto> searchSpotifyMusicList = spotifyApiService.searchSpotifyMusicList(
                "디토");

        for (SpotifySearchResultDto spotifySearchResultDto : searchSpotifyMusicList) {
            logger.debug("Spotify Music Title : {}", spotifySearchResultDto.getMusicTitle());
        }

        return ResponseEntity.ok().build();
    }


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

        logger.debug("요청으로 넘어온 값 : {}", map.get("storyContent"));
        logger.debug("요청으로 넘어온 값 타입 확인 : {}", map.get("storyContent").getClass().getName());
        Gson gson = new Gson();
        String jsonStoryContent = gson.toJson(map.get("storyContent"));
        logger.debug("변환 후 : {}", jsonStoryContent);
        logger.debug("변환 후 타입 : {}", jsonStoryContent.getClass().getName());

        Object objUserSeq = map.get("userSeq");
        logger.debug("objUserSeq : {}", objUserSeq);
        long longUserSeq = Long.valueOf(String.valueOf(objUserSeq));
        logger.debug("longUserSeq : {}", longUserSeq);

        StoryRequestDto storyRequestDto = StoryRequestDto.builder()
                .userSeq(longUserSeq)
                .storyTitle((String) map.get("storyTitle"))
                .storyContent(jsonStoryContent)
                .storyMusicTitle((String) map.get("storyMusicTitle"))
                .storyMusicArtist((String) map.get("storyMusicArtist"))
                .build();

        // 이미 신청한 사연이 있음
        storyService.isUniqueStory(longUserSeq);


        // 사연 등록 비즈니스로직 수행
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

//    /**
//     * 사연 중복 검사 (신청 사연 리스트에 memberSeq로 신청한 사연이 있는지 여부 true  : 중복 있음 (사연 신청 불가능) false : 중복 없음 (사연 신청
//     * 가능)
//     */
//    @GetMapping("/unique/{userSeq}")
//    public ResponseEntity<Boolean> isUniqueStory(@PathVariable long userSeq) {
//        try {
//            return ResponseEntity.ok(storyService.isUniqueStory(userSeq));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

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
