package com.musicat.service;

import com.musicat.data.dto.alert.AlertAllModifyRequestDto;
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
    public void insertAlert(AlertInsertRequestDto alertInsertRequestDto) {
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
    public void deleteAlert(long alertSeq) {
        Alert alert = alertRepository.findById(alertSeq)
                .orElseThrow(() -> new EntityNotFoundException());
        alertRepository.delete(alert);

    }

    /**
     * 알림 전체 조회 (userSeq 기준)
     * @param userSeq
     * @return List<Alert>
     * @throws Exception
     */
    public List<Alert> getAlertList(long userSeq) {
        Optional<List<Alert>> alertList = alertRepository.findAllByUserSeq(userSeq);

        if (alertList.isPresent() && alertList.get().size() > 0) {
            return alertList.get();
        } else {
            throw new EntityNotFoundException("Alert not found with userSeq: " + userSeq);
        }
    }

    /**
     * 알림 상세 조회 (alertSeq 기준)
     * @param alertSeq
     * @return
     * @throws Exception
     */
    public Alert getAlert(long alertSeq) {
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
    public void modifyAlert(AlertModifyRequestDto alertModifyRequestDto) {
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
    public List<Alert> getAlertListByCondition(String condition, long userSeq, String query) {
        Optional<List<Alert>> alertList = null;

        switch (condition) {
            case "title": // 제목
                alertList = alertRepository.findAllByUserSeqAndAlertTitleContaining(userSeq, query);
                break;
            case "content": // 내용
                alertList = alertRepository.findAllByUserSeqAndAlertContentContaining(userSeq, query);
                break;
            case "any": // 제목 + 내용
                alertList = alertRepository.findAllByUserSeqAndAlertTitleContainingOrAlertContentContaining(userSeq, query, query);
                break;
        }

        if (alertList.isPresent() && alertList.get().size() > 0) {
            return alertList.get();
        } else {
            throw new EntityNotFoundException("주어진 조건으로 검색한 결과가 없습니다.");
        }
    }

    /**
     * 안읽은 알림 개수 조회
     * @param userSeq
     * @return
     */
    public long getAlertCountByAlertIsReadFalse(long userSeq) {
        return alertRepository.countByUserSeqAndAlertIsReadFalse(userSeq);
    }

    /**
     * 안읽은 알림 전체 읽음 처리
     * @param userSeq
     * @param alertAllModifyRequestDto
     */
    @Transactional
    public void modifyAllAlert(long userSeq, AlertAllModifyRequestDto alertAllModifyRequestDto) {
        List<Alert> alertList = alertRepository.findAllByUserSeq(userSeq)
                .orElseThrow(() -> new EntityNotFoundException("조회 결과가 없습니다."));

        for (Alert alert : alertList) {
            alert.setAlertIsRead(true);
        }

    }

}
