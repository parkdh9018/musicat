package com.musicat.data.entity;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Data
public class Story {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "story_seq")
  @NotNull
  private long storySeq;

  @Column(name = "member_id")
  @NotNull
  private long memberId;

  @CreatedDate
  @Column(name = "story_created_at")
  @NotNull
  private LocalDateTime storyCreatedAt;

  @Column(name = "story_is_read", columnDefinition = "TINYINT(1)")
  private boolean storyIsRead;

  @Column(name = "story_is_valid", columnDefinition = "TINYINT(1)")
  private boolean storyIsValid; // 사연 유효성 검사 결과



}
