package com.musicat.service;


import com.musicat.data.dto.NoticeDto;
import com.musicat.data.entity.Notice;
import com.musicat.data.repository.NoticeRepository;
import java.time.LocalDate;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;

  // 삽입
  public Notice insertNotice(NoticeDto noticeDto) {
    Notice notice = new Notice(noticeDto);
    Notice result = noticeRepository.save(notice);
    return result;
  }

  // 조회
  public Notice getNotice(Long noticeSeq) {
    return noticeRepository
        .findById(noticeSeq)
        .orElseThrow(IllegalArgumentException::new);
  }

  // 수정
  public Notice modifyNotice(NoticeDto noticeDto) {
    Notice notice = noticeRepository
        .findById(noticeDto.getNoticeSeq())
        .orElseThrow(IllegalArgumentException::new);

    notice.setNoticeTitle(notice.getNoticeTitle());
    notice.setNoticeContent(noticeDto.getNoticeContent());
    notice.setNoticeUpdatedAt(LocalDate.now());

    return notice;

  }

  // 삭제
  public void deleteNotice(Long noticeId) {
    noticeRepository.deleteById(noticeId);
  }


}
