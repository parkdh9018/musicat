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

    @PostMapping("/story")
    public ResponseEntity<StoryInsertResponseDto> insertStory(@RequestBody StoryRequestDto storyRequestDto) {
        try {
            System.out.println("사연 등록하러 가자 !!");
            StoryInsertResponseDto storyInsertResponseDto = storyService.insertStory(storyRequestDto);
            return ResponseEntity.ok(storyInsertResponseDto);
        } catch (Exception e) {
            System.err.print("예외 발생");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
