package com.musicat.data.dto.notice;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class NoticeModifyDto {

    private long noticeSeq;

    private String noticeTitle;

    private String noticeContent;
}
