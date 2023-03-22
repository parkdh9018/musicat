package com.musicat.service;

import com.musicat.data.dto.alert.AlertInsertRequestDto;
import com.musicat.data.dto.alert.AlertModifyRequestDto;
import com.musicat.data.entity.Alert;
import com.musicat.data.repository.AlertRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertService {

    // repository 정의
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

    /**
     * 알림 삭제
     * @param alertSeq
     * @throws Exception
     */
    @Transactional
    public void deleteAlert(long alertSeq) throws Exception {
        Optional<Alert> alert = alertRepository.findById(alertSeq);
        if (alert.isPresent()) {
            alertRepository.delete(alert.get());
        } else {
            //throw new AlertNotFoundException("Alert not found with seq: " + alertSeq); // Todo : 커스텀 예외 처리 예정
            throw new EntityNotFoundException("Alert not found with alertSeq: " + alertSeq);
        }
    }

    /**
     * 알림 전체 조회 (userSeq 기준)
     * @param userSeq
     * @return List<Alert>
     * @throws Exception
     */
    public List<Alert> getAlertList(long userSeq) throws Exception {
        Optional<List<Alert>> alertList = alertRepository.findAllByUserSeq(userSeq);

        if (alertList.isPresent() && alertList.get().size() > 0) {
            return alertList.get();
        } else {
            // Todo : 커스텀 예외 처리 예정
            throw new EntityNotFoundException("Alert not found with userSeq: " + userSeq);
        }
    }

    /**
     * 알림 상세 조회 (alertSeq 기준)
     * @param alertSeq
     * @return
     * @throws Exception
     */
    public Alert getAlert(long alertSeq) throws Exception {
        Optional<Alert> alert = alertRepository.findById(alertSeq);

        if (alert.isPresent()) {
            return alert.get();
        } else {
            throw new EntityNotFoundException("Alert not found with alertSeq: " + alertSeq);
        }
    }

    /**
     * 알림 수정 (읽음 처리)
     * @param alertModifyRequestDto
     * @throws Exception
     */
    @Transactional
    public void modifyAlert(AlertModifyRequestDto alertModifyRequestDto) throws Exception {

        Optional<Alert> alert = alertRepository.findById(alertModifyRequestDto.getAlertSeq());

        if (alert.isPresent()) {
            alert.get().setAlertIsRead(alertModifyRequestDto.isAlertIsRead());
            alertRepository.save(alert.get());
        } else {
            throw new EntityNotFoundException("Alert not found with alertSeq: " + alertModifyRequestDto.getAlertSeq());
        }
    }

    /**
     * 알림 조건부 검색
     * @param condition
     * @param userSeq
     * @param query
     * @return
     * @throws Exception
     */
    public List<Alert> getAlertListByCondition(int condition, long userSeq, String query) throws Exception {

        Optional<List<Alert>> alertList = null;

        switch (condition) {
            case 0: // 제목
                alertList = alertRepository.findAllByUserSeqAndAlertTitleContaining(userSeq, query);
                break;
            case 1: // 내용
                alertList = alertRepository.findAllByUserSeqAndAlertContentContaining(userSeq, query);
                break;
            case 2: // 제목 + 내용
                alertList = alertRepository.findAllByUserSeqAndAlertTitleContainingOrAlertContentContaining(userSeq, query, query);
                break;
        }

        if (alertList.isPresent() && alertList.get().size() > 0) {
            return alertList.get();
        } else {
            // Todo : 커스텀 예외 처리 예정
            throw new EntityNotFoundException();
        }
    }

}
