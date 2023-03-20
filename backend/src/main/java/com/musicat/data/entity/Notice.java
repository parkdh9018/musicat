package com.musicat.data.entity;


import com.musicat.data.dto.NoticeDto;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_seq")
  private long noticeSeq;

  @Column(name = "member_seq")
  private long memberSeq;

  @Column(name = "notice_title")
  private String noticeTitle;

  @Column(name = "notice_content")
  private String noticeContent;

  @Column(name = "notice_created_at")
  private LocalDate noticeCreatedAt;

  @Column(name = "notice_updated_at")
  private LocalDate noticeUpdatedAt;

  public Notice(NoticeDto noticeDto) {
    this.memberSeq = noticeDto.getMemberSeq();
    this.noticeTitle = noticeDto.getNoticeTitle();
    this.noticeContent = noticeDto.getNoticeContent();
    this.noticeCreatedAt = LocalDate.now();
    this.noticeUpdatedAt = LocalDate.now();
  }
}
