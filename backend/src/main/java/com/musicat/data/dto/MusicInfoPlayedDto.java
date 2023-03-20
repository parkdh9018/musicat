package com.musicat.data.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicInfoPlayedDto {

  private long memberSeq;

  private LocalDateTime musicPlayedAt;

  private LocalDateTime musicCreatedAt;

  private String musicName;

  private String musicArtist;

  private String youtubeVideoId;

  private long musicLength;

  private long musicPlayedMs;

  private String musicCover;

}
