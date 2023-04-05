package com.musicat.data.dto.user;

// user list에 들어갈 dto

import lombok.*;

/*

List를 반환하는데 사용되는 DTO

 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPageDto {

  private long userSeq;
  private String userNickname;
  private String userEmail;

  // 2022년 12월 10일
  private String userCreatedAt;
  private boolean userIsChattingBan;
  private boolean userIsBan;
  private boolean userIsUser;

}
