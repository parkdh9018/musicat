package com.musicat.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicRequestDto {

  private long memberSeq;

  private String musicName;

  private String musicArtist;

  private String youtubeVideoId;

  private Long musicLength;

  private String musicCover;

}
