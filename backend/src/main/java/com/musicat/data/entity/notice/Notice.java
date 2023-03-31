package com.musicat.data.entity.notice;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.musicat.data.entity.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notice")
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_seq")
  private long noticeSeq;

  // 회원과 1:N 연관관계 설정
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_seq")
  private User user;

  @Column(name = "notice_title")
  private String noticeTitle;

  @Column(name = "notice_content")
  private String noticeContent;

  @CreatedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "notice_created_at")
  private LocalDateTime noticeCreatedAt;

}
