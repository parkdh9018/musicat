package com.musicat.data.dto.user;


import lombok.*;

import java.time.LocalDateTime;

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
