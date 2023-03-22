package com.musicat.data.dto.notice;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeWriteDto {


    private long userSeq;
    private String noticeTitle;
    private String noticeContent;



}
