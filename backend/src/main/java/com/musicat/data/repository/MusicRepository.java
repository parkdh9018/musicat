package com.musicat.data.repository;

import com.musicat.data.entity.Music;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

  public Optional<Music> findByMusicNameAndMusicArtist(String musicName, String musicArtist);

  public Optional<List<Music>> findTop10ByMusicIsPlayedOrderByMusicSeqAsc(boolean musicIsPlayed);

  public Optional<Music> findTop1ByMusicIsPlayedOrderByMusicSeqAsc(boolean musicIsPlayed);
}
