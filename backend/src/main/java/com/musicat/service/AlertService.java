package com.musicat.service;

import com.musicat.data.dto.alert.request.AlertAllModifyRequestDto;
import com.musicat.data.dto.alert.response.AlertDetailResponseDto;
import com.musicat.data.dto.alert.request.AlertInsertRequestDto;
import com.musicat.data.dto.alert.response.AlertListResponseDto;
import com.musicat.data.dto.alert.request.AlertModifyRequestDto;
import com.musicat.data.entity.Alert;
import com.musicat.data.repository.AlertRepository;
import com.musicat.util.AlertBuildUtil;
import com.musicat.util.ConstantUtil;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertService {

    // repository 정의
    private final AlertRepository alertRepository;

    // util 정의
    private final ConstantUtil constantUtil;
    private final AlertBuildUtil alertBuildUtil;


    /**
     * 알림 등록
     *
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
     *
     * @param alertSeq
     * @throws Exception
     */
    @Transactional
    public void deleteAlert(long alertSeq) {
        Alert alert = alertRepository.findById(alertSeq)
                .orElseThrow(EntityNotFoundException::new);
        alertRepository.delete(alert);

    }

    /**
     * 알림 전체 조회 (userSeq 기준)
     *
     * @param userSeq
     * @return List<Alert>
     * @throws Exception
     */
    public Page<AlertListResponseDto> getAlertList(long userSeq, String query, int page) {

        PageRequest pageable = PageRequest.of(page, constantUtil.ALERT_PAGE_SIZE);
        // Page 타입으로 리턴
        Page<Alert> alertListPage = null;
        if (query.equals("")) { // 아무것도 입력하지 않은 경우
            alertListPage = alertRepository.findAllByUserSeq(userSeq, pageable);
        } else { // 무언가 입력한 경우
            alertListPage = alertRepository.findAllByUserSeqAndAlertTitleContainingOrAlertContentContaining(userSeq, query, query, pageable);
        }

        List<AlertListResponseDto> alertListDtos = alertListPage.getContent().stream()
                .map(alertBuildUtil::alertToAlertListDto)
                .collect(Collectors.toList());

        Pageable alertListDtoPageable = PageRequest.of(alertListPage.getNumber(),
                alertListPage.getSize(), alertListPage.getSort());

        return new PageImpl<>(alertListDtos,
                alertListDtoPageable, alertListPage.getTotalElements());
    }

    /**
     * 알림 상세 조회 (alertSeq 기준) : 읽는 순간 읽음 처리 !
     *
     * @param alertSeq
     * @return
     * @throws Exception
     */
    @Transactional
    public AlertDetailResponseDto getAlert(long alertSeq) {
        Alert alert = alertRepository.findById(alertSeq).orElseThrow(
                () -> new EntityNotFoundException("알림 정보가 존재하지 않습니다.")
        );

        alert.setAlertIsRead(true);

        return alertBuildUtil.alertToAlertDetailDto(alert);
    }

//    /**
//     * 알림 수정 (읽음 처리)
//     *
//     * @param alertModifyRequestDto
//     * @throws Exception
//     */
//    @Transactional
//    public void modifyAlert(AlertModifyRequestDto alertModifyRequestDto) {
//        Alert alert = alertRepository.findById(alertModifyRequestDto.getAlertSeq())
//                .orElseThrow(EntityNotFoundException::new);
//
//        alert.setAlertIsRead(alertModifyRequestDto.isAlertIsRead());
//    }


    /**
     * 안읽은 알림 개수 조회
     *
     * @param userSeq
     * @return
     */
    public long getAlertCountByAlertIsReadFalse(long userSeq) {
        return alertRepository.countByUserSeqAndAlertIsReadFalse(userSeq);
    }

    /**
     * 안읽은 알림 전체 읽음 처리
     *
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
