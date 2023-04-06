package com.musicat.data.entity.radio;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "CacheMusic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class CacheMusic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cache_music_seq")
  private long cacheMusicSeq;

  @Column(name = "music_title")
  @NotNull
  private String musicTitle;

  @Column(name = "music_artist")
  @NotNull
  private String musicArtist;

  @Column(name = "music_youtube_id")
  private String musicYoutubeId;

  @Column(name = "music_length")
  private long musicLength;

}
