package com.musicat.data.entity.radio;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Story {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "story_seq")
  @NotNull
  private long storySeq; // 사연 일련 번호

  @Column(name = "user_seq")
  @NotNull
  private long userSeq; // 회원 일련 번호

  @CreatedDate
  @Column(name = "story_created_at")
  private LocalDateTime storyCreatedAt; // 사연 생성 시간

  @Column(name = "story_readed", columnDefinition = "TINYINT(1)")
  private boolean storyReaded; // 사연 읽음 여부

  @Column(name = "story_valid", columnDefinition = "TINYINT(1)")
  private Boolean storyValid; // 사연 유효성 검사 결과 (GPT요청안감 / false / true)

  @Column(name = "story_title")
  private String storyTitle; // 사연 제목

  @Column(name = "story_content")
  private String storyContent; // 사연 내용

  @Column(name = "story_music_title")
  @NotNull
  private String storyMusicTitle; // 사연 신청곡 제목

  @Column(name = "story_music_artist")
  @NotNull
  private String storyMusicArtist; // 사연 신청곡 가수

  @Column(name = "story_music_cover")
  private String storyMusicCover; // 사연 신청곡 커버 이미지 url

  @Column(name = "story_music_length")
  private long storyMusicLength; // 사연 신청곡 길이

  @Column(name = "story_music_youtube_id")
  private String storyMusicYoutubeId; // 사연 신청곡 유튜브 아이디

  @Column(name = "story_reaction")
  private String storyReaction; // 사연에 대한 GPT 응답 텍스트

  @Column(name = "storyOutro")
  private String storyOutro; // 사연 종료(신청곡이 끝나는 타이밍)에 대한 GPT 응답 텍스트

}