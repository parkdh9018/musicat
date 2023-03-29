package com.musicat.util;

import com.musicat.data.dto.alert.response.AlertDetailResponseDto;
import com.musicat.data.dto.alert.response.AlertListResponseDto;
import com.musicat.data.entity.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertBuildUtil {

    private final ConstantUtil constantUtil;

    public AlertListResponseDto alertToAlertListDto(Alert alert) {
        return AlertListResponseDto.builder()
                .alertSeq(alert.getAlertSeq())
                .alertTitle(alert.getAlertTitle())
                .alertCreatedAt(alert.getAlertCreatedAt().format(constantUtil.simpleFormatter))
                .alertIsRead(alert.isAlertIsRead())
                .build();
    }

    public AlertDetailResponseDto alertToAlertDetailDto(Alert alert) {
        return AlertDetailResponseDto.builder()
                .alertSeq(alert.getAlertSeq())
                .alertTitle(alert.getAlertTitle())
                .alertContent(alert.getAlertContent())
                .alertCreatedAt(alert.getAlertCreatedAt().format(constantUtil.detailFormatter))
                .build();
    }

    /**
     * 다른 로직에 따른 알림 생성
     */
    public Alert alertBuild(long userSeq, String title, String content) {
        return Alert.builder()
                .userSeq(userSeq)
                .alertTitle(title)
                .alertContent(content)
                .build();
    }

}
