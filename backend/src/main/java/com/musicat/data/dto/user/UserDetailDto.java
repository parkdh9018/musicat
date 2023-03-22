package com.musicat.data.dto.user;


/*
회원 상세 조회 Dto
 */

import com.musicat.data.entity.user.Authority;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailDto {

    private long userSeq;
    private String userNickname;
    private String userProfileImage;
    private String userThumbnailImage;
    private String userEmail;

    // 2022년 12월 10일
    private String userCreatedAt;
    private long userMoney;
    private List<UserMoneyLogDto> userMoneyLogDtoList = new ArrayList<>();
    private List<String> userAuthority = new ArrayList<>();
    private int userUnreadMessage;
    private boolean userIsDarkmode;
    private boolean userIsChattingBan;
    private boolean userIsBan;
    private boolean userIsUser;

}
