package com.musicat.data.repository;

import com.musicat.data.entity.Music;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

//  @Query(value = "SELECT m FROM Music m  WHERE m.musicName = :musicName AND m.musicArtist = :musicArtist AND m.musicIsPlayed = false")
//  public Optional<Music> findByNameAndArtist(@Param("musicName") String musicName, @Param("musicArtist") String musicArtist);
  public Optional<Music> findByMusicNameAndMusicArtistAndMusicIsPlayedFalse(String musicName,
      String musicArtist);

//  @Query(value = "SELECT m FROM Music m WHERE m.memberSeq = :memberSeq AND m.musicIsPlayed = false")
//  Optional<Music> findUnplayedMusicByMemberSeq(@Param("memberSeq") long memberSeq);
  public Optional<Music> findByMemberSeqAndMusicIsPlayedFalse(Long memberSeq);

//  @Query(value = "SELECT COUNT(*) FROM Music m WHERE m.musicSeq < :musicSeq AND m.musicIsPlayed = false")
//  public int countUnplayedMusicBefore(@Param("musicSeq") Long musicSeq);
  public int countByMusicSeqLessThanAndMusicIsPlayedFalse(Long musicSeq);

  public Optional<List<Music>> findTop10ByMusicIsPlayedFalseOrderByMusicSeqAsc();

  public Optional<Music> findTop1ByMusicIsPlayedTrueOrderByMusicSeqDesc();

  public Optional<Music> findTop1ByMusicIsPlayedFalseOrderByMusicSeqAsc();

}
