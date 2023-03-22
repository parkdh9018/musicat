package com.musicat.service;

import com.musicat.data.dto.alert.AlertInsertRequestDto;
import com.musicat.data.entity.Alert;
import com.musicat.data.repository.AlertRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertService {

    // 알림 레퍼지토리 선언
    private final AlertRepository alertRepository;

    /**
     * 알림 등록
     * @param alertInsertRequestDto
     * @throws Exception
     */
    @Transactional
    public void insertAlert(AlertInsertRequestDto alertInsertRequestDto) throws Exception {
        // 1. request -> entity
        // 2. entity save
        Alert alert = Alert.builder()
                .userSeq(alertInsertRequestDto.getUserSeq())
                .alertTitle(alertInsertRequestDto.getAlertTitle())
                .alertContent(alertInsertRequestDto.getAlertContent())
                .build();

        alertRepository.save(alert);
    }

    @Transactional
    public void deleteAlert(long alertSeq) throws Exception {
        // Todo : 알람이 존재하지 않은 경우는 404 NotFound 커스텀 예외 처리 예정
        Optional<Alert> alert = alertRepository.findById(alertSeq);
        if (alert.isPresent()) {
            alertRepository.delete(alert.get());
        } else {
            //throw new AlertNotFoundException("Alert not found with seq: " + alertSeq); // Todo : 커스텀 예외 처리
            throw new EntityNotFoundException("Alert not found with seq: " + alertSeq);
        }
    }

}
