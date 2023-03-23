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
public class AlertListResponseDto {

    private long alertSeq;

    private String alertTitle;

    private String alertCreatedAt;

    private boolean alertIsRead;

}
