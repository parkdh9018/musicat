package com.musicat.util;

import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryInsertResponseDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.Story;
import org.springframework.stereotype.Component;

@Component
public class StoryBuilderUtil {

    // Dto -> Entity
    public Story buildStoryEntity(StoryRequestDto storyRequestDto) {
        return Story.builder()
                .memberSeq(storyRequestDto.getMemberSeq())
                .storyTitle(storyRequestDto.getStoryTitle())
                .storyContent(storyRequestDto.getStoryContent())
                .build();
    }

    // Story -> StoryInfoDto
    public StoryInfoDto buildStoryInfoDto(Story story) {
        return StoryInfoDto.builder()
                .storySeq(story.getStorySeq())
                .memberSeq(story.getMemberSeq())
                .storyTitle(story.getStoryTitle())
                .storyCreatedAt(story.getStoryCreatedAt())
                .storyIsRead(story.isStoryIsRead())
                .storyIsValid(story.isStoryIsValid())
                .storyWavFileDirectoryRoot(story.getStoryWavFileDirectoryRoot())
                .build();
    }

    // Story -> StoryInsertDto
    public StoryInsertResponseDto buildStoryInsertResponseDto(StoryInfoDto storyInfoDto, int playOrder) {
        return StoryInsertResponseDto.builder()
                .playOrder(playOrder)
                .storyInfoDto(storyInfoDto)
                .build();
    }

}
