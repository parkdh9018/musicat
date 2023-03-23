package com.musicat.service;


import com.musicat.data.dto.notice.NoticeDetailDto;
import com.musicat.data.dto.notice.NoticeListDto;
import com.musicat.data.entity.notice.Notice;
import com.musicat.data.repository.NoticeRepository;

import com.musicat.util.ConstantUtil;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.musicat.util.NoticeBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


  // 공지사항 최신 3개 조회
  public List<NoticeListDto> getNoticeTop3() {
    List<Notice> noticeList = noticeRepository.findTop3ByOrderByNoticeCreatedAtDesc().orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));
    return noticeList.stream()
            .map(notice -> noticeBuilderUtil.noticeToNoticeListDto(notice))
            .collect(Collectors.toList());
  }

  /**
   * 공지사항 10개 조회 (페이지네이션)
   * @param page
   * @return Page<NoticeListDto>
   */
  public Page<NoticeListDto> getNoticeList(int page) {
    PageRequest pageable = PageRequest.of(page, constantUtil.NOTICE_PAGE_SIZE);
    // Page 타입으로 리턴
    Page<Notice> noticeListPage = noticeRepository.findAll(pageable);


    // Dto를 담은 List 타입으로 변환 (N + 1 문제 해결 및 원하는 데이터만 넘기기 위해서)
    List<NoticeListDto> noticeListDtos = noticeListPage.getContent().stream()
            .map(noticeBuilderUtil::noticeToNoticeListDto)
            .collect(Collectors.toList());


    // List 타입 -> Page 타입
    Pageable noticeListDtoPageable = PageRequest.of(noticeListPage.getNumber(),
            noticeListPage.getSize(), noticeListPage.getSort());
    Page<NoticeListDto> noticeListDtoPage = new PageImpl<>(noticeListDtos, noticeListDtoPageable, noticeListPage.getTotalElements());

    return noticeListDtoPage;
  }

  // 공지사항 상세 조회
  public NoticeDetailDto getNoticeDetail(long noticeSeq) {
    Notice notice = noticeRepository.findById(noticeSeq).orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));
    return noticeBuilderUtil.noticeToNoticeDetailDto(notice);
  }





}
