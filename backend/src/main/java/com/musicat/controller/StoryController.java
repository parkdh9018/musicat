package com.musicat.controller;

import com.musicat.data.dto.story.StoryInsertResponseDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
