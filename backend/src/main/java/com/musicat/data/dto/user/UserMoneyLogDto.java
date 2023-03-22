package com.musicat.data.dto.user;


import lombok.*;

import java.time.LocalDateTime;

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
