package com.musicat.data.repository.radio;

import com.musicat.data.entity.radio.Story;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {

  // 읽지 않은 사연 조회 (valid 검사 실패하면 Readed = true 되므로. Readed가 null 이거나 false 인 경우 조회)
  public Optional<Story> findByUserSeqAndStoryReadedFalseOrUserSeqAndStoryReadedNull(long userSeq, long userSeq2);

}
