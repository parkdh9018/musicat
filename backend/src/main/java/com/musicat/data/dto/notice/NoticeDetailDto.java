package com.musicat.data.dto.notice;

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
public class NoticeDetailDto {

  private long noticeSeq;
  private String userNickname;
  private String noticeTitle;
  private String noticeContent;
  private String noticeCreatedAt; // 2022년 11월 20일 (수) 18:20
  private String noticeUpdatedAt; // 2022년 11월 20일 (수) 18:20


}
