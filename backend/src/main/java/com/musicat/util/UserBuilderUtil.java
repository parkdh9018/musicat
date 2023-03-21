package com.musicat.util;


import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.data.dto.user.UserListDto;
import com.musicat.data.entity.user.Authority;
import com.musicat.data.entity.user.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserBuilderUtil {

    // 2022년 12월 10일 같은 형색
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");


    /*
    user -> userDetailDto
     */
    public UserDetailDto userToUserDetailDto(User user) {

        List<String> authorities = user.getUserAuthority().stream()
                .map(Authority::getAuthorityName) // Authority 클래스에 'getRole' 메소드가 있다고 가정합니다.
                .collect(Collectors.toList());

        return UserDetailDto.builder()
                .userSeq(user.getUserSeq())
                .userNickname(user.getUserNickname())
                .userProfileImage(user.getUserProfileImage())
                .userThumbnailImage(user.getUserThumbnailImage())
                .userEmail(user.getUserEmail())
                .userCreatedAt(user.getUserCreatedAt().format(formatter))
                .userMoney(user.getUserMoney())
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
                .userCreatedAt(user.getUserCreatedAt().format(formatter))
                .userIsChattingBan(user.isUserIsChattingBan())
                .userIsBan(user.isUserIsBan())
                .userIsUser(user.isUserIsUser())
                .build();
    }

}
