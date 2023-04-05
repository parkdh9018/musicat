package com.musicat.data.dto.story;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoryKafkaDto {

  private long storySeq;

  private long userSeq;

  private String storyTitle;

  private String storyContent;

  private String storyMusicTitle;

  private String storyMusicArtist;

}
