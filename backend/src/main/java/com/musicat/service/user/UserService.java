package com.musicat.service.user;


import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.data.dto.user.UserListDto;
import com.musicat.data.dto.user.UserModifyRequestDto;
import com.musicat.data.dto.user.UserMoneyLogDto;
import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.AuthorityRepository;
import com.musicat.data.repository.MoneyLogRepository;
import com.musicat.data.repository.UserRepository;
import com.musicat.util.UserBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


/*

유저의 비즈니스 로직을 처리하는 class

*/

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserBuilderUtil userBuilderUtil;
    private final MoneyLogRepository moneyLogRepository;


    // 회원 상세 조회
    public UserDetailDto getUserDetail(long userSeq)   {
        User user = userRepository
                .findById(userSeq)
                .orElseThrow(() -> new RuntimeException("User not found with userSeq: " + userSeq));
        return userBuilderUtil.userToUserDetailDto(user);
    }

    // 회원 정보 수정 (닉네임 변경)
    public void modifyUserNickname(UserModifyRequestDto userModifyRequestDto) {
        User user = userRepository.findById(userModifyRequestDto.getUserSeq()).orElseThrow(() -> new RuntimeException());
        user.setUserNickname(userModifyRequestDto.getUserNickname());
    }

    // 회원 탈퇴
    public void deleteUser(long userSeq) {
        userRepository.deleteById(userSeq);
    }

    // 회원 재화 내역 상세 조회
    public UserMoneyLogDto getUserMoneyLogDetail(long moneyLogSeq) {
        MoneyLog moneyLog = moneyLogRepository.findById(moneyLogSeq).orElseThrow(() -> new RuntimeException());
        return userBuilderUtil.moneyLogToUserMoneyLogDto(moneyLog);
    }





}
