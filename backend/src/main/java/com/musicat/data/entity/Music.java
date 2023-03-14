package com.musicat.data.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Music")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Music {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "music_seq")
  private long musicSeq;

  @Column(name = "member_seq", nullable = false)
  private long memberSeq;

  @Column(name = "music_name", nullable = false)
  private String musicName;

  @Column(name = "music_artist", nullable = false)
  private String musicArtist;

  @Column(name = "music_genre", nullable = false)
  private String musicGenre;

  @Column(name = "music_length", nullable = false)
  private long musicLength;

  @Column(name = "music_cover", nullable = false)
  private String musicCover;

  @Column(name = "music_played_at")
  private LocalDateTime musicPlayedAt;

  @Column(name = "music_played_ms")
  private long musicPlayedMs;

  @CreatedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "music_created_at")
  private LocalDateTime musicCreatedAt;

  @Column(name = "music_is_played", columnDefinition = "TINYINT(1)")
  private boolean musicIsPlayed;


  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.musicCreatedAt = now;
    this.musicPlayedAt = null;
    this.musicPlayedMs = 0L;
    this.musicIsPlayed = false;
  }

}
