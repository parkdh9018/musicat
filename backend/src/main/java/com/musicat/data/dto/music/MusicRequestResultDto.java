package com.musicat.data.dto.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicRequestResultDto {

  public int status;

  public MusicInfoDto musicInfo;

  public int playOrder;

}
