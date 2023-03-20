package com.musicat.data.dto;


import lombok.Data;

@Data
public class NoticeDto {

  private long noticeSeq;

  private long memberSeq;
  private String noticeTitle;
  private String noticeContent;



}
