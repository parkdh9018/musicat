package com.musicat.data.entity;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Music")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Music {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "music_seq")
  private long musicSeq;

  @Column(name = "user_seq")
  @NotNull
  private long userSeq;

  @Column(name = "music_title")
  @NotNull
  private String musicTitle;

  @Column (name = "music_genre")
  @NotNull
  private String musicGenre;

  @Column (name = "music_artist")
  @NotNull
  private String musicArtist;

  @Column (name = "music_album")
  @NotNull
  private String musicAlbum;

  @Column (name = "music_image")
  private String musicImage;

  @Column (name = "music_youtube_id")
  @NotNull
  private String musicYoutubeId;

  @Column (name = "music_length")
  @NotNull
  private long musicLength;

  @Column (name = "music_intro")
  private String musicIntro;

  @Column (name = "music_outro")
  private String musicOutro;

  @Column (name = "music_release_date")
  private String musicReleaseDate;

  @CreatedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "music_created_at")
  private LocalDateTime musicCreatedAt;

  @Column(name = "music_played", columnDefinition = "TINYINT(1)")
  private boolean musicPlayed;

}
