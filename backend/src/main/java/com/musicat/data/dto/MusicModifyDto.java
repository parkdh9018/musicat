package com.musicat.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicModifyDto {

  private long memberSeq;

  private String musicName;

  private String musicArtist;

  private String musicGenre;

  private long musicLength;

  private String musicCover;
}
