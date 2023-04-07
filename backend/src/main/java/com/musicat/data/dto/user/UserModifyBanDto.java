package com.musicat.data.dto.user;


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
public class UserModifyBanDto {

  private long userSeq;
  private String moneyLogType;
  private long moneyLogChange;
  private String moneyLogCreatedAt;

}
