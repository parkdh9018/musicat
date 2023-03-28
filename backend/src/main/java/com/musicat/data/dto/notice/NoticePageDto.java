package com.musicat.data.dto.notice;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticePageDto {

    private long noticeSeq;
    private String noticeTitle;
    private String noticeCreatedAt; // (2022년 12월 14일)

}
