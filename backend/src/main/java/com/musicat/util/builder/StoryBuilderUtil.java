package com.musicat.util.builder;

import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryKafkaDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.radio.Story;
import com.musicat.util.ConstantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoryBuilderUtil {

  private final ConstantUtil constantUtil;

  // Dto -> Story(Entity)
  public Story buildStoryEntity(StoryRequestDto storyRequestDto) {
    return Story.builder()
        .userSeq(storyRequestDto.getUserSeq())
        .storyTitle(storyRequestDto.getStoryTitle())
        .storyContent(storyRequestDto.getStoryContent())
        .storyMusicTitle(storyRequestDto.getStoryMusicTitle())
        .storyMusicArtist(storyRequestDto.getStoryMusicArtist())
        .storyMusicCover(storyRequestDto.getStoryMusicCover())
        .storyMusicYoutubeId(storyRequestDto.getMusicYoutubeId())
        .storyMusicLength(storyRequestDto.getMusicLength())
        .build();
  }

  // Story -> StoryInfoDto
  // Todo : 프론트 측에서 필요한 데이터만 제공할 예정
  public StoryInfoDto buildStoryInfoDto(Story story) {
    return StoryInfoDto.builder()
//                .storySeq(story.getStorySeq())
//                .userSeq(story.getUserSeq())
        .storyCreatedAt(constantUtil.detailFormatter.format(story.getStoryCreatedAt()))
        .storyReaded(story.isStoryReaded())
        .storyValid(story.getStoryValid())
        .storyTitle(story.getStoryTitle())
//                .storyContent(story.getStoryContent())
        .storyMusicTitle(story.getStoryMusicTitle())
        .storyMusicArtist(story.getStoryMusicArtist())
        .storyMusicCover(story.getStoryMusicCover())
//                .storyMusicLength(story.getStoryMusicLength())
        .storyMusicYoutubeId(story.getStoryMusicYoutubeId())
        .storyReaction(story.getStoryReaction())
        .storyOutro(story.getStoryOutro())
        .build();
  }

  // Story -> StoryKafkaDto
  public StoryKafkaDto buildStoryKafkaDto(Story story) {
    return StoryKafkaDto.builder()
        .storySeq(story.getStorySeq())
        .userSeq(story.getUserSeq())
        .storyTitle(story.getStoryTitle())
        .storyContent(story.getStoryContent())
        .storyMusicTitle(story.getStoryTitle())
        .storyMusicArtist(story.getStoryMusicArtist())
        .build();
  }


}
