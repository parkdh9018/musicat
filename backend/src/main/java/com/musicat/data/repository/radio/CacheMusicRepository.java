package com.musicat.data.repository.radio;

import com.musicat.data.entity.radio.CacheMusic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheMusicRepository extends JpaRepository<CacheMusic, Long> {

  public Optional<CacheMusic> findByMusicTitleAndMusicArtist(String musicTitle, String musicArtist);
}
