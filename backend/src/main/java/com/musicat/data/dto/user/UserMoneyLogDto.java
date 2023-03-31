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
public class UserMoneyLogDto {

  private long moneyLogSeq;
  private String moneyLogType;
  private String moneyLogDetail;

  private long moneyLogChange;

  private String moneyLogCreatedAt;


}
