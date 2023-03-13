package com.musicat.data.dto;

import lombok.Data;

@Data
public class MusicDto {

  private long musicSeq;

  private long memberSeq;

  private String musicName;

  private String musicArtist;

  private String musicGenre;

  private long musicLength;

  private String musicCover;
}
