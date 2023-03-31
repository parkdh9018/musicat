package com.musicat.service.notice;


import com.musicat.data.dto.notice.NoticeDetailDto;
import com.musicat.data.dto.notice.NoticeListDto;
import com.musicat.data.dto.notice.NoticePageDto;
import com.musicat.data.entity.notice.Notice;
import com.musicat.data.repository.notice.NoticeRepository;

import com.musicat.util.ConstantUtil;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.musicat.util.builder.NoticeBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

  private final ConstantUtil constantUtil;
  private final NoticeRepository noticeRepository;
  private final NoticeBuilderUtil noticeBuilderUtil;

  /**
   * 공지사항 최신 3개 조회
   *
   * @return
   */
  public List<NoticeListDto> getNoticeTop3() {
    List<Notice> noticeList = noticeRepository.findTop3ByOrderByNoticeCreatedAtDesc()
        .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));
    return noticeList.stream()
        .map(notice -> noticeBuilderUtil.noticeToNoticeListDto(notice))
        .collect(Collectors.toList());
  }

  /**
   * 공지사항 10개 조회 (페이지네이션)
   *
   * @param page
   * @param query
   * @return
   */
  public Page<NoticePageDto> getNoticePage(int page, String query) {
    PageRequest pageable = PageRequest.of(page, constantUtil.NOTICE_PAGE_SIZE,
        Sort.by(Sort.Direction.DESC, "noticeCreatedAt"));
    Page<Notice> noticePage = noticeRepository.findByNoticeTitleContainingIgnoreCaseOrderByNoticeCreatedAtDesc(
        query, pageable);
    return noticePage.map(noticeBuilderUtil::noticeToNoticePageDto);
  }

  /**
   * 공지사항 상세 조회
   *
   * @param noticeSeq
   * @return
   */
  public NoticeDetailDto getNoticeDetail(long noticeSeq) {
    Notice notice = noticeRepository.findById(noticeSeq)
        .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));
    return noticeBuilderUtil.noticeToNoticeDetailDto(notice);
  }


}
