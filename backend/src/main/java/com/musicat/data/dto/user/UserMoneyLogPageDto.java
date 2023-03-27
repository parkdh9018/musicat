package com.musicat.data.dto.user;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMoneyLogPageDto {
    private long moneyLogSeq;
    private String moneyLogType;
    private long moneyLogChange;
    private String moneyLogCreatedAt;
}
