package com.musicat.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicModifyResponseDto {

  public int status;

  public MusicInfoDto musicInfoDto;

  public int playOrder;

}
