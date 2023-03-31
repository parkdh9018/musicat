package com.musicat.service.user;


import com.musicat.data.dto.user.UserConfigDto;
import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.data.dto.user.UserInfoJwtDto;
import com.musicat.data.dto.user.UserModifyRequestDto;
import com.musicat.data.dto.user.UserModifyResponseDto;
import com.musicat.data.dto.user.UserMoneyDto;
import com.musicat.data.dto.user.UserMoneyLogDto;
import com.musicat.data.dto.user.UserMoneyLogPageDto;
import com.musicat.data.dto.user.UserUnreadMessageDto;
import com.musicat.data.entity.item.Badge;
import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import com.musicat.data.entity.user.UserAttendance;
import com.musicat.data.repository.user.AlertRepository;
import com.musicat.data.repository.user.AuthorityRepository;
import com.musicat.data.repository.user.MoneyLogRepository;
import com.musicat.data.repository.user.UserAttendanceRepository;
import com.musicat.data.repository.user.UserRepository;
import com.musicat.jwt.TokenProvider;
import com.musicat.util.ConstantUtil;
import com.musicat.util.builder.UserBuilderUtil;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/*

유저의 비즈니스 로직을 처리하는 class

*/

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final UserAttendanceRepository userAttendanceRepository;
  private final AuthorityRepository authorityRepository;
  private final AlertService alertService;
  private final UserBuilderUtil userBuilderUtil;
  private final MoneyLogRepository moneyLogRepository;
  private final AlertRepository alertRepository;
  private final TokenProvider tokenProvider;
  private final ConstantUtil constantUtil;


  /**
   * 회원 상세 조회
   * 이메일 + 생성날짜
   *
   * @param token
   * @return
   */
  public UserDetailDto getUserDetail(String token) {
    // 토큰에서 유저 정부 추출
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    System.out.println(userInfo.toString());

    // user 정보 획득
    User user = userRepository
        .findById(userInfo.getUserSeq())
        .orElseThrow(() -> new RuntimeException());
    return userBuilderUtil.userToUserDetailDto(user);
  }

  /**
   * 회원 재화 조회
   *
   * @param token
   * @return
   */
  public UserMoneyDto getUserMoney(String token) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    // user 정보 획득
    User user = userRepository
        .findById(userInfo.getUserSeq())
        .orElseThrow(() -> new RuntimeException());

    return userBuilderUtil.userToUserMoneyDto(user);
  }

  /**
   * 회원 읽지 않은 메세지 조회
   *
   * @param token
   * @return
   */
  public UserUnreadMessageDto getUserUnreadMessage(String token) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    long userUnreadMessage = alertRepository.countByUserSeqAndAlertIsReadFalse(
        userInfo.getUserSeq());

    return userBuilderUtil.userToUserUnreadMessageDto(userUnreadMessage);
  }

  /**
   * 회원 설정 조회 (다크모드 + 아이템 3종)
   *
   * @param token
   * @return
   */
  public UserConfigDto getUserConfig(String token) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    // user 정보 획득
    User user = userRepository
        .findById(userInfo.getUserSeq())
        .orElseThrow(() -> new RuntimeException());

    return userBuilderUtil.userToUserConfigDto(user);
  }

  /**
   * 회원 재화 내역 조회
   *
   * @param token
   * @param page
   * @return
   */
  public Page<UserMoneyLogPageDto> getUserMoneyLog(String token, int page) {
    // user 정보 획득
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);
    User user = userRepository
        .findById(userInfo.getUserSeq())
        .orElseThrow(() -> new RuntimeException());

    // page 설정 : 현재 페이지, 페이지 사이즈, 정렬
    PageRequest pageable = PageRequest.of(page, constantUtil.MONEY_PAGE_SIZE,
        Sort.by(Sort.Direction.DESC, "moneyLogCreatedAt"));
    Page<MoneyLog> moneyLogList = moneyLogRepository.findByUser(user, pageable);

    // 꼭 확인하기!!
    return moneyLogList.map(userBuilderUtil::moneyLogToUserMoneyLogPageDto);
  }

  /**
   * 다크모드 수정
   *
   * @param token
   */
  public void modifyDarkmode(String token) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

    User user = userRepository
        .findById(userInfo.getUserSeq())
        .orElseThrow(() -> new RuntimeException());

    if (user.isUserIsDarkmode()) {
      user.setUserIsDarkmode(false);
    } else {
      user.setUserIsDarkmode(true);
    }
  }

  /**
   * 회원 정보 수정 (닉네임 변경)
   * @param token
   * @param userModifyRequestDto
   * @return
   */
  public UserModifyResponseDto modifyUserNickname(String token,
      UserModifyRequestDto userModifyRequestDto) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);
    User user = userRepository.findByUserSeqWithAuthorities(userInfo.getUserSeq()).orElseThrow();
    user.setUserNickname(userModifyRequestDto.getUserNickname());

    return UserModifyResponseDto.builder()
        .token(tokenProvider.createUserToken(user))
        .build();
  }

  /**
   * 회원 탈퇴
   *
   * @param token
   */
  public void deleteUser(String token) {
    UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);
    userRepository.deleteById(userInfo.getUserSeq());
  }

  /**
   * 회원 재화 내역 상세 조회
   *
   * @param moneyLogSeq
   * @return
   */
  public UserMoneyLogDto getUserMoneyLogDetail(long moneyLogSeq) {
    MoneyLog moneyLog = moneyLogRepository.findById(moneyLogSeq)
        .orElseThrow(() -> new RuntimeException());
    return userBuilderUtil.moneyLogToUserMoneyLogDto(moneyLog);
  }

  /**
   * 회원 출석 츄르 지급
   *
   * @param userSeq
   * @return
   */
  public User attend(Long userSeq) {
    User user = userRepository.findById(userSeq).orElseThrow();
    LocalDate today = LocalDate.now();
    Optional<UserAttendance> existingAttendance = userAttendanceRepository.findByUserAndDate(user,
        today);

    // 출석이 처음이라면 재화를 주고 출석 내용 저장
    if (!existingAttendance.isPresent()) {
      UserAttendance userAttendance = UserAttendance.builder()
          .user(user)
          .date(today)
          .build();

      // 100 츄르 지급
      user.setUserMoney(user.getUserMoney() + constantUtil.TODAY_MONEY);
      userAttendanceRepository.save(userAttendance);

      // 알림 저장
      alertService.insertAlertByAlertType(user.getUserSeq(), "today");
    }

    return user;
  }

  /**
   * Ban 조회
   *
   * @param userSeq
   * @return
   */
  public boolean isBan(long userSeq) {
    User user = userRepository.findById(userSeq)
        .orElseThrow(() -> new EntityNotFoundException("회원 정보가 존재하지 않습니다."));

    return user.isUserIsBan();
  }

  /**
   * 회원 뱃지 조회
   *
   * @param userSeq
   * @return
   */
  public Badge getBadge(long userSeq) {
    User user = userRepository.findById(userSeq)
        .orElseThrow(() -> new EntityNotFoundException("회원 정보가 존재하지 않습니다."));

    return user.getBadge();
  }


}
