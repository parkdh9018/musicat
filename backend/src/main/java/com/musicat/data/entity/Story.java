package com.musicat.data.entity;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Story {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "story_seq")
  @NotNull
  private long storySeq;

  @Column(name = "member_seq")
  @NotNull
  private long memberSeq;

  @CreatedDate
  @Column(name = "story_created_at")
  private LocalDateTime storyCreatedAt; // 사연 생성 시간

  @Column(name = "story_read_at")
  private LocalDateTime storyReadAt; // 사연 읽은 시간

  @Column(name = "story_is_read", columnDefinition = "TINYINT(1)")
  private boolean storyIsRead; // 사연 읽음 여부

  @Column(name = "story_is_valid", columnDefinition = "TINYINT(1)")
  private boolean storyIsValid; // 사연 유효성 검사 결과

  @Column(name = "story_title")
  private String storyTitle; // 사연 제목

  @Column(name = "story_content")
  private String storyContent; // 사연 내용

}
