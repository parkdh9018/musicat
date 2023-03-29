package com.musicat.service.user;


import com.musicat.data.dto.user.*;
import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import com.musicat.data.entity.user.UserAttendance;
import com.musicat.data.repository.*;
import com.musicat.jwt.TokenProvider;
import com.musicat.util.ConstantUtil;
import com.musicat.util.UserBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
    private final UserBuilderUtil userBuilderUtil;
    private final MoneyLogRepository moneyLogRepository;
    private final AlertRepository alertRepository;
    private final TokenProvider tokenProvider;
    private final ConstantUtil constantUtil;


    // 회원 상세 조회
    // 이메일 + 생성날짜
    public UserDetailDto getUserDetail(String token)   {
        // 토큰에서 유저 정부 추출
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        System.out.println(userInfo.toString());

        // user 정보 획득
        User user = userRepository
                .findById(userInfo.getUserSeq())
                .orElseThrow(() -> new RuntimeException());
        return userBuilderUtil.userToUserDetailDto(user);
    }

    // 회원 재화 조회
    public UserMoneyDto getUserMoney(String token) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        // user 정보 획득
        User user = userRepository
                .findById(userInfo.getUserSeq())
                .orElseThrow(() -> new RuntimeException());

        return userBuilderUtil.userToUserMoneyDto(user);
    }

    // 회원 읽지 않은 메세지 조회
    public UserUnreadMessageDto getUserUnreadMessage(String token) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        long userUnreadMessage = alertRepository.countByUserSeqAndAlertIsReadFalse(userInfo.getUserSeq());

        return userBuilderUtil.userToUserUnreadMessageDto(userUnreadMessage);
    }

    // 회원 설정 조회 (다크모드 + 아이템 3종)
    public UserConfigDto getUserConfig(String token) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        // user 정보 획득
        User user = userRepository
                .findById(userInfo.getUserSeq())
                .orElseThrow(() -> new RuntimeException());

        return userBuilderUtil.userToUserConfigDto(user);
    }

    // 회원 재화 내역 조회
    public Page<UserMoneyLogPageDto> getUserMoneyLog(String token, int page) {
        // user 정보 획득
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);
        User user = userRepository
                .findById(userInfo.getUserSeq())
                .orElseThrow(() -> new RuntimeException());

        // page 설정 : 현재 페이지, 페이지 사이즈, 정렬
        PageRequest pageable = PageRequest.of(page, constantUtil.MONEY_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "moneyLogCreatedAt"));
        Page<MoneyLog> moneyLogList = moneyLogRepository.findByUser(user, pageable);

        // 꼭 확인하기!!
        return moneyLogList.map(userBuilderUtil::moneyLogToUserMoneyLogPageDto);
    }

    // 회원 다크모드 설정
    public void modifyDarkmode(String token) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);

        User user = userRepository
                .findById(userInfo.getUserSeq())
                .orElseThrow(() -> new RuntimeException());

        if(user.isUserIsDarkmode()) {
            user.setUserIsDarkmode(false);
        }
        else {
            user.setUserIsDarkmode(true);
        }
    }

    // 회원 정보 수정 (닉네임 변경)
    public UserModifyResponseDto modifyUserNickname(String token, UserModifyRequestDto userModifyRequestDto) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);
        User user = userRepository.findByUserSeqWithAuthorities(userInfo.getUserSeq()).orElseThrow();
        user.setUserNickname(userModifyRequestDto.getUserNickname());

        return UserModifyResponseDto.builder()
                .token(tokenProvider.createUserToken(user))
                .build();
    }

    // 회원 탈퇴
    public void deleteUser(String token) {
        UserInfoJwtDto userInfo = tokenProvider.getUserInfo(token);
        userRepository.deleteById(userInfo.getUserSeq());
    }

    // 회원 재화 내역 상세 조회
    public UserMoneyLogDto getUserMoneyLogDetail(long moneyLogSeq) {
        MoneyLog moneyLog = moneyLogRepository.findById(moneyLogSeq).orElseThrow(() -> new RuntimeException());
        return userBuilderUtil.moneyLogToUserMoneyLogDto(moneyLog);
    }

    // 회원 출석 츄르 지급
    public User attend(Long userSeq) {
        User user = userRepository.findById(userSeq).orElseThrow();
        LocalDate today = LocalDate.now();
        Optional<UserAttendance> existingAttendance = userAttendanceRepository.findByUserAndDate(user, today);

        // 출석이 처음이라면 재화를 주고 출석 내용 저장
        if (!existingAttendance.isPresent()) {
            UserAttendance userAttendance = UserAttendance.builder()
                    .user(user)
                    .date(today)
                    .build();

            // 100 츄르 지급
            user.setUserMoney(user.getUserMoney() + constantUtil.TODAY_MONEY);
            userAttendanceRepository.save(userAttendance);
        }

        return user;
    }




}
