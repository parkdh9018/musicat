package com.musicat.data.dto.music;

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

  private String youtubeVideoId;

  private long musicLength;

  private String musicCover;
}
