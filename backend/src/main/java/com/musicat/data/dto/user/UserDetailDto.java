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

    private String userEmail;
    private String userCreatedAt;

}
