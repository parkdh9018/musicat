package com.musicat.controller;

import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.service.StoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class StoryController {

    // Service 정의
    private final StoryService storyService;



    /**
     * 사연 신청
     */
    @PostMapping("")
    public ResponseEntity<?> insertStory(@RequestBody StoryRequestDto storyRequestDto) {
        try {
            if (storyService.isUniqueStory(storyRequestDto.getUserSeq())) return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 이미 신청한 사연이 있음

            // 유효성 검사 로직
            // 유튜브 검사 로직
            // 카프카로 전송 후 검사 결과를 받는다.
            storyService.insertStory(storyRequestDto);

            return ResponseEntity.ok().build(); // 사연 신청 성공. (신청한 사연 정보 반환)
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사연 1개 조회 (읽을 사연) (가장 먼저 신청한 사연 중 1개 조회)
     */
    @GetMapping("/story")
    public ResponseEntity<?> getTopStory() {
        try {
            Object result = storyService.getTopStoryInfo();

            if (result == null) return ResponseEntity.noContent().build(); // 사연 검색 결과 없음

            return ResponseEntity.ok(result); // 사연 검색 성공. (검색한 사연 정보 반환)
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사연 중복 검사 (신청 사연 리스트에 memberSeq로 신청한 사연이 있는지 여부
     * true  : 중복 있음 (사연 신청 불가능)
     * false : 중복 없음 (사연 신청 가능)
     */
    @GetMapping("/unique/{userSeq}")
    public ResponseEntity<Boolean> isUniqueStory(@PathVariable long userSeq) {
        try {
            return ResponseEntity.ok(storyService.isUniqueStory(userSeq));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    /**
//     * 사연 상세 조회
//     */
//    @GetMapping("/story/{storySeq}")
//    public ResponseEntity<?> getStory(@PathVariable long storySeq) {
//        try {
//            Object result = storyService.getStory(storySeq);
//
//            if (result == null) return ResponseEntity.noContent().build(); // 사연 검색 결과 없음
//
//            return ResponseEntity.ok(result); // 사연 검색 성공. (검색한 사연 정보 반환)
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    /**
//     * 사연 삭제
//     * 0 : (204) 사연이 존재하지 않음
//     * 1 : (200) 사연 삭제 성공
//     */
//    @DeleteMapping("/story/{storySeq}")
//    public ResponseEntity<Void> deleteStory(@PathVariable long storySeq) {
//        try {
//            if (storyService.deleteStory(storySeq) == 0) return ResponseEntity.noContent().build(); // 사연 검색 결과 없음
//
//            return ResponseEntity.ok().build(); // 사연 삭제 성공
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


}
