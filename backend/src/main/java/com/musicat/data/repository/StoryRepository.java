package com.musicat.data.repository;

import com.musicat.data.entity.Story;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {

    // (유효성 검사에 통과, 읽지 않은) 사연 리스트에서 1개의 사연 가져오기 (신청 순서로 정렬되어있습니다.)
    public Optional<Story> findTop1ByStoryIsReadFalseAndStoryIsValidTrueOrderByStoryCreatedAt();

    // (memberSeq로 조회, 읽지 않은 사연) 사연 리스트에서 1개의 사연 가져오기
    public Optional<Story> findByMemberSeqAndStoryIsReadFalse(long memberSeq);

    // 신청 사연 리스트에서 순번 조회
    public int countByStorySeqLessThanAndStoryIsReadFalseAndStoryIsValidTrue(long storySeq);
}
