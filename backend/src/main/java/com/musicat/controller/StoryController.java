package com.musicat.controller;

import com.musicat.data.dto.story.StoryInsertResponseDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.service.StoryService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    /**
     * 사연 신청
     */
    @PostMapping("/story")
    public ResponseEntity<?> insertStory(@RequestBody StoryRequestDto storyRequestDto) {
        try {
            if (storyService.isUniqueStory(storyRequestDto.getMemberSeq())) return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 신청한 사연이 있습니다.");

            return ResponseEntity.ok(storyService.insertStory(storyRequestDto));
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
            return ResponseEntity.ok(storyService.getTopStoryInfo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사연 중복 검사 (신청 사연 리스트에 memberSeq로 신청한 사연이 있는지 여부 판별
     */
    @GetMapping("/story/unique/{memberSeq}")
    public ResponseEntity<Boolean> isUniqueStory(@PathVariable long memberSeq) {
        try {
            return ResponseEntity.ok(storyService.isUniqueStory(memberSeq));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사연 상세 조회
     */
    @GetMapping("/story/{storySeq}")
    public ResponseEntity<?> getStory(@PathVariable long storySeq) {
        try {
            return ResponseEntity.ok(storyService.getStory(storySeq));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사연 삭제
     * 0 : (204) 사연이 존재하지 않음
     * 1 : (200) 사연 삭제 성공
     */
    @DeleteMapping("/story/{storySeq}")
    public ResponseEntity<Void> deleteStory(@PathVariable long storySeq) {
        try {
            if (storyService.deleteStory(storySeq) == 0) return ResponseEntity.noContent().build();

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
