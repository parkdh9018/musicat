package com.musicat.data.dto.story;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StoryRequestDto {

  private long userSeq;

  private String storyTitle;

  private String storyContent;

  private String storyMusicTitle;

  private String storyMusicArtist;

  private String storyMusicCover;

  private String musicYoutubeId;

  private long musicLength;

}
