package com.musicat.data.repository.notice;

import com.musicat.data.entity.notice.Notice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  // 작성 기준으로 최신순 3개를 가져오는 메소드
  Optional<List<Notice>> findTop3ByOrderByNoticeCreatedAtDesc();

  Page<Notice> findAll(Pageable pageable);


  // 공지사항 제목 검색 page 형식으로 최신순으로 받아오기
  Page<Notice> findByNoticeTitleContainingIgnoreCaseOrderByNoticeCreatedAtDesc(String query,
      Pageable pageable);


  Page<Notice> findAllByNoticeTitleContainingOrNoticeContentContaining(Pageable pageable,
      String noticeTitle, String noticeContent);
}
