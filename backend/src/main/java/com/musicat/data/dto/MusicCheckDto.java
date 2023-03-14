package com.musicat.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicCheckDto {

  private long memberSeq;

  private long musicSeq;

  private int musicOrder;

}
