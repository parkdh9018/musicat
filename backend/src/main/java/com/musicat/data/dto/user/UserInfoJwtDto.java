package com.musicat.data.dto.user;


import lombok.*;

/*

token에서 사용자 정보를 담는 DTO

 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoJwtDto {
    private long userSeq;
    private String userNickname;
    private String userProfileImage;
    private boolean userIsChattingBan;
    private boolean userIsBan;
}
