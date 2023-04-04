package com.musicat.controller.radio;

import com.google.gson.Gson;
import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.service.radio.StoryService;

import java.util.HashMap;
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
   * @return 201, 409, 500
   */
  @PostMapping("")
  public ResponseEntity<?> insertStory(/**@RequestBody StoryRequestDto storyRequestDto**/
      @RequestBody
      Map<String, Object> map) {

    // spotify + youtube 검색 결과 = storySong
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
        .musicYoutubeId(storySongMap.get("musicYoutubeId"))
        .musicLength(musicLength)
        .build();

    // 이미 신청한 사연이 있는지 검증
    // 이미 존재할 경우 409 Exception 발생
    storyService.isUniqueStory(longUserSeq);

    // 사연 등록 서비스 호출
    storyService.insertStory(storyRequestDto);

    // 201 HttpStatus Code 리턴
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 사연 중복 검사
   * @param userSeq
   * @return 200, 409, 500
   */
  @GetMapping("/unique/{userSeq}")
  public ResponseEntity<?> isUniqueStory(@PathVariable long userSeq) {
    // Todo : 토큰에서 userSeq 가져오는 로직으로 변경해야함
    // 이미 신청한 사연이 존재할 경우 409 Exception 발생
    storyService.isUniqueStory(userSeq);

    // 200 HttpStatus Code 리턴
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
   *
   * @param storySeq
   * @return 200, 404, 500
   */
  @DeleteMapping("/{storySeq}")
  public ResponseEntity<Void> deleteStory(@PathVariable long storySeq) {
    storyService.deleteStory(storySeq);

    return ResponseEntity.ok().build();
  }

}
