package com.musicat.data.dto.user;


/*
회원 상세 조회 Dto
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailDto {

  private String userEmail;
  private String userCreatedAt;

}
