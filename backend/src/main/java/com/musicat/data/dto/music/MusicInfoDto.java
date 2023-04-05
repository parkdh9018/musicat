package com.musicat.data.dto.music;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.Local;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicInfoDto {

  private long musicSeq;
  private long userSeq;
  private String musicTitle;
  private String musicArtist;
  private String musicAlbum;
  private String musicImage;
  private String musicYoutubeId;
  private long musicLength;
  private String musicReleaseDate;
  private LocalDateTime musicCreatedAt;
  private boolean musicIsPlayed;

}
