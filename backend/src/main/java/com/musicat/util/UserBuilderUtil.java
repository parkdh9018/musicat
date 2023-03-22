package com.musicat.util;


import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.data.dto.user.UserListDto;
import com.musicat.data.dto.user.UserMoneyLogDto;
import com.musicat.data.entity.user.Authority;
import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserBuilderUtil {

    private final ConstantUtil constantUtil;

    /*
    user -> userDetailDto
     */
    public UserDetailDto userToUserDetailDto(User user) {

        // 권한 설정 저장
        List<String> authorities = user.getUserAuthority().stream()
                .map(Authority::getAuthorityName) // Authority 클래스에 'getRole' 메소드가 있다고 가정합니다.
                .collect(Collectors.toList());

        // moneylog 가져오기
        List<UserMoneyLogDto> userMoneyLogDtoList = user.getUserMoneyLogList().stream()
                .map(moneyLog -> new UserMoneyLogDto(
                        moneyLog.getMoneyLogSeq(),
                        moneyLog.getMoneyLogType(),
                        moneyLog.getMoneyLogDetail(),
                        moneyLog.getMoneyLogChange(),
                        moneyLog.getMoneyLogCreatedAt().format(constantUtil.simpleFormatter)
                ))
                .collect(Collectors.toList());

        return UserDetailDto.builder()
                .userSeq(user.getUserSeq())
                .userNickname(user.getUserNickname())
                .userProfileImage(user.getUserProfileImage())
                .userThumbnailImage(user.getUserThumbnailImage())
                .userEmail(user.getUserEmail())
                .userCreatedAt(user.getUserCreatedAt().format(constantUtil.simpleFormatter))
                .userMoney(user.getUserMoney())
                .userMoneyLogDtoList(userMoneyLogDtoList)
                .userAuthority(authorities)
                .userUnreadMessage(user.getUserUnreadMessage())
                .userIsDarkmode(user.isUserIsDarkmode())
                .userIsBan(user.isUserIsBan())
                .userIsUser(user.isUserIsUser())
                .build();
    }

    public UserListDto userToUserListDto(User user) {
        return UserListDto.builder()
                .userSeq(user.getUserSeq())
                .userNickname(user.getUserNickname())
                .userEmail(user.getUserEmail())
                .userCreatedAt(user.getUserCreatedAt().format(constantUtil.simpleFormatter))
                .userIsChattingBan(user.isUserIsChattingBan())
                .userIsBan(user.isUserIsBan())
                .userIsUser(user.isUserIsUser())
                .build();
    }

    public UserMoneyLogDto moneyLogToUserMoneyLogDto(MoneyLog moneyLog) {
        return UserMoneyLogDto.builder()
                .moneyLogSeq(moneyLog.getMoneyLogSeq())
                .moneyLogType(moneyLog.getMoneyLogType())
                .moneyLogDetail(moneyLog.getMoneyLogDetail())
                .moneyLogChange(moneyLog.getMoneyLogChange())
                .moneyLogCreatedAt(moneyLog.getMoneyLogCreatedAt().format(constantUtil.detailFormatter))
                .build();
    }



}
