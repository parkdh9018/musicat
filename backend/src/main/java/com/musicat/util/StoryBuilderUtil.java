package com.musicat.util;

import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.Story;
import org.springframework.stereotype.Component;

@Component
public class StoryBuilderUtil {

    // Dto -> Entity
    public Story buildStoryEntity(StoryRequestDto storyRequestDto) {
        return Story.builder()
                .userSeq(storyRequestDto.getUserSeq())
                .storyTitle(storyRequestDto.getStoryTitle())
                .storyContent(storyRequestDto.getStoryContent())
                .storyMusicName(storyRequestDto.getStoryMusicName())
                .storyMusicArtist(storyRequestDto.getStoryMusicArtist())
                .build();
    }

    // Story -> StoryInfoDto
    public StoryInfoDto buildStoryInfoDto(Story story) {
        return StoryInfoDto.builder()
                .storySeq(story.getStorySeq())
                .userSeq(story.getUserSeq())
                .storyCreatedAt(story.getStoryCreatedAt())
                .storyReadAt(story.getStoryReadAt())
                .storyIsRead(story.isStoryIsRead())
                .storyIsValid(story.isStoryIsValid())
                .storyIsReady(story.isStoryIsReady())
                .storyTitle(story.getStoryTitle())
                .storyContent(story.getStoryContent())
                .storyWavFileDirectoryRoot(story.getStoryWavFileDirectoryRoot())
                .storyMusicName(story.getStoryMusicName())
                .storyMusicArtist(story.getStoryMusicArtist())
                .storyMusicIsPlayed(story.isStoryMusicIsPlayed())
                .storyMusicCover(story.getStoryMusicCover())
                .storyMusicPlayedMs(story.getStoryMusicPlayedMs())
                .storyMusicLength(story.getStoryMusicLength())
                .storyMusicYoutubeId(story.getStoryMusicYoutubeId())
                .build();
    }



}
