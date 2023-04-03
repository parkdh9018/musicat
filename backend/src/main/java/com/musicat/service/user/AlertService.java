package com.musicat.service.user;

import com.musicat.data.dto.alert.request.AlertAllModifyRequestDto;
import com.musicat.data.dto.alert.response.AlertDetailResponseDto;
import com.musicat.data.dto.alert.request.AlertInsertRequestDto;
import com.musicat.data.dto.alert.response.AlertListResponseDto;
import com.musicat.data.dto.user.UserInfoJwtDto;
import com.musicat.data.entity.user.Alert;
import com.musicat.data.repository.user.AlertRepository;
import com.musicat.jwt.TokenProvider;
import com.musicat.util.builder.AlertBuilderUtil;
import com.musicat.util.ConstantUtil;
import java.util.List;
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
  private final AlertBuilderUtil alertBuilderUtil;
  private final TokenProvider tokenProvider;

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
   * @param token
   * @return List<Alert>
   * @throws Exception
   */
  public Page<AlertListResponseDto> getAlertList(String token, String query, int page) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    PageRequest pageable = PageRequest.of(page, constantUtil.ALERT_PAGE_SIZE);
    // Page 타입으로 리턴
    Page<Alert> alertListPage = null;
    if (query.equals("")) { // 아무것도 입력하지 않은 경우
      alertListPage = alertRepository.findAllByUserSeq(userInfo.getUserSeq(), pageable);
    } else { // 무언가 입력한 경우
      alertListPage = alertRepository.findByUserSeqAndAlertTitleContainingOrUserSeqAndAlertContentContaining(
          userInfo.getUserSeq(), query, userInfo.getUserSeq(), query, pageable);
    }

    List<AlertListResponseDto> alertListDtos = alertListPage.getContent().stream()
        .map(alertBuilderUtil::alertToAlertListDto)
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

    return alertBuilderUtil.alertToAlertDetailDto(alert);
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
   * @param token
   * @return
   */
  public long getAlertCountByAlertIsReadFalse(String token) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);
    return alertRepository.countByUserSeqAndAlertIsReadFalse(userInfo.getUserSeq());
  }

  /**
   * 안읽은 알림 전체 읽음 처리
   *
   * @param token
   * @param alertAllModifyRequestDto
   */
  @Transactional
  public void modifyAllAlert(String token, AlertAllModifyRequestDto alertAllModifyRequestDto) {

    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    List<Alert> alertList = alertRepository.findAllByUserSeq(userInfo.getUserSeq())
        .orElseThrow(() -> new EntityNotFoundException("조회 결과가 없습니다."));

    for (Alert alert : alertList) {
      alert.setAlertIsRead(true);
    }
  }


  /**
   * 다른 서비스 활동에 따른 알림 저장 1. 출석 츄르 지급 2. 채팅 금지 3. 채팅 금지 해제 4. 활동 금지 5. 활동 금지 해재 6. 신청곡 재생 7. 사연 당첨
   */

  // 출석 츄르 지급 알림
  public void insertAlertByAlertType(long userSeq, String alertType) {

    String title = "";
    String content = "";

    // 1. 출석 츄르 지급
    if (alertType.equals(constantUtil.ALERT_TODAY_TYPE)) {
      title = constantUtil.ALERT_TODAY_TITLE;
      content = constantUtil.ALERT_TODAY_CONTENT;
    }
    // 2. 채팅 금지
    else if (alertType.equals(constantUtil.ALERT_CHATTING_BAN_TYPE)) {
      title = constantUtil.ALERT_CHATTING_BAN_TITLE;
      content = constantUtil.ALERT_CHATTING_BAN_CONTENT;
    }
    // 3. 채팅 금지 해재
    else if (alertType.equals(constantUtil.ALERT_NOT_CHATTING_BAN_TYPE)) {
      title = constantUtil.ALERT_NOT_CHATTING_BAN_TITLE;
      content = constantUtil.ALERT_NOT_CHATTING_BAN_CONTENT;
    }
    // 4. 활동 금지
    else if (alertType.equals(constantUtil.ALERT_BAN_TYPE)) {
      title = constantUtil.ALERT_BAN_TITLE;
      content = constantUtil.ALERT_BAN_CONTENT;
    }
    // 5. 활동 금지 해재
    else if (alertType.equals(constantUtil.ALERT_NOT_BAN_TYPE)) {
      title = constantUtil.ALERT_NOT_BAN_TITLE;
      content = constantUtil.ALERT_NOT_BAN_CONTENT;
    }
    // 6. 신청곡 재생
    else if (alertType.equals(constantUtil.ALERT_MUSIC_TYPE)) {
      title = constantUtil.ALERT_MUSIC_TITLE;
      content = constantUtil.ALERT_MUSIC_CONTENT;
    }
    // 7. 사연 당첨
    else if (alertType.equals(constantUtil.ALERT_STORY_TYPE)) {
      title = constantUtil.ALERT_STORY_TITLE;
      content = constantUtil.ALERT_STORY_CONTENT;
    }

    // type에 따른 alert 저장
    Alert alert = alertBuilderUtil.alertBuild(userSeq, title, content);
    alertRepository.save(alert);

  }


}
