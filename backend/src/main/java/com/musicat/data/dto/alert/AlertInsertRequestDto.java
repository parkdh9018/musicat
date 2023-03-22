package com.musicat.data.dto.alert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AlertInsertRequestDto {

    private long userSeq;

    private String alertTitle;

    private String alertContent;
}
