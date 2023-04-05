package com.musicat.data.repository.radio;

import com.musicat.data.entity.radio.Music;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

  //  @Query(value = "SELECT m FROM Music m  WHERE m.musicName = :musicName AND m.musicArtist = :musicArtist AND m.musicIsPlayed = false")
//  public Optional<Music> findByNameAndArtist(@Param("musicName") String musicName, @Param("musicArtist") String musicArtist);
  public Optional<Music> findByMusicTitleAndMusicArtistAndMusicPlayedFalse(String musicTitle,
      String musicArtist);

  //  @Query(value = "SELECT m FROM Music m WHERE m.memberSeq = :memberSeq AND m.musicIsPlayed = false")
//  Optional<Music> findUnplayedMusicByMemberSeq(@Param("memberSeq") long memberSeq);
  public Optional<Music> findByUserSeqAndMusicPlayedFalse(Long userSeq);

  //  @Query(value = "SELECT COUNT(*) FROM Music m WHERE m.musicSeq < :musicSeq AND m.musicIsPlayed = false")
//  public int countUnplayedMusicBefore(@Param("musicSeq") Long musicSeq);
  public int countByMusicSeqLessThanAndMusicPlayedFalse(Long musicSeq);

  public Optional<List<Music>> findTop10ByMusicPlayedFalseOrderByMusicSeqAsc();

  public Optional<Music> findTop1ByMusicPlayedTrueOrderByMusicSeqDesc();

  public Optional<Music> findTop1ByMusicPlayedFalseOrderByMusicSeqAsc();

}
