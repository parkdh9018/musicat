package com.musicat.data.entity;

import com.musicat.data.dto.MusicDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
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

  @CreatedDate
  @Column(name = "music_created_at", nullable = false, updatable = false)
  private LocalDateTime musicCreatedAt;

  @Column(name = "music_is_played", columnDefinition = "TINYINT(1)")
  private boolean musicIsPlayed;


  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.musicCreatedAt = now;
    this.musicPlayedAt = null;
    this.musicIsPlayed = false;
  }

  public Music(MusicDto musicDto) {
    this.musicName = musicDto.getMusicName();
    this.musicArtist = musicDto.getMusicArtist();
    this.memberSeq = musicDto.getMemberSeq();
    this.musicGenre = musicDto.getMusicGenre();
    this.musicCover = musicDto.getMusicCover();
    this.musicLength = musicDto.getMusicLength();

  }

}
