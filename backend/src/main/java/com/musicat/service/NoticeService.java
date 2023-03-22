package com.musicat.service;


import com.musicat.data.dto.notice.NoticeDetailDto;
import com.musicat.data.dto.notice.NoticeListDto;
import com.musicat.data.entity.notice.Notice;
import com.musicat.data.entity.user.User;
import com.musicat.data.repository.NoticeRepository;

import javax.transaction.Transactional;

import com.musicat.util.NoticeBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final NoticeBuilderUtil noticeBuilderUtil;


  // 공지사항 최신 3개 조회
  public List<NoticeListDto> getNoticeTop3() {
    List<Notice> noticeList = noticeRepository.findTop3ByOrderByNoticeCreatedAtDesc().orElseThrow();
    return noticeList.stream()
            .map(notice -> noticeBuilderUtil.noticeToNoticeListDto(notice))
            .collect(Collectors.toList());
  }
  
  // 공지사항 전부 조회
  public List<NoticeListDto> getNoticeList(int page) {
    return null;
  }

  // 공지사항 상세 조회
  public NoticeDetailDto getNoticeDetail(long noticeSeq) {
    Notice notice = noticeRepository.findById(noticeSeq).orElseThrow();
    return noticeBuilderUtil.noticeToNoticeDetailDto(notice);
  }





}
