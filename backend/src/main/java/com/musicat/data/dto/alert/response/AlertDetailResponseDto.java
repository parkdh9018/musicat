package com.musicat.data.dto.alert.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AlertDetailResponseDto {

  private long alertSeq;

  private String alertTitle;

  private String alertContent;

  private String alertCreatedAt;

}
