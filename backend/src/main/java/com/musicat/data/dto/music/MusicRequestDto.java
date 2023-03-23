package com.musicat.data.dto.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicRequestDto {

  private long userSeq;
  private String musicTitle;
  private String musicArtist;
  private String musicAlbum;
  private String musicImage;
  private String musicGenre;
  private String musicReleaseDate;

}
