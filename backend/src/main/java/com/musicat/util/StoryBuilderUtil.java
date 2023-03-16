package com.musicat.util;

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

}
