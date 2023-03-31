package com.musicat.controller.notice;


import com.musicat.data.dto.notice.NoticeDetailDto;
import com.musicat.data.dto.notice.NoticeListDto;
import com.musicat.service.notice.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@CrossOrigin
public class NoticeController {

  private final NoticeService noticeService;

  /**
   * 공지사항 작성 시간 기준 최신 데이터 3개 가져오기
   *
   * @return
   */
  @GetMapping("/top3")
  public ResponseEntity<?> getNoticeTop3() {
    List<NoticeListDto> noticeTop3 = noticeService.getNoticeTop3();
    return ResponseEntity.ok(noticeTop3);
  }

  /**
   * 공지사항 10개 조회 (페이지네이션)
   *
   * @param page
   * @return
   */
  @GetMapping("")
  public ResponseEntity<?> getNoticeList(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "") String query) {
    return ResponseEntity.ok(noticeService.getNoticePage(page, query));
  }

  /**
   * 공지사항 상세 조회
   *
   * @param noticeSeq
   * @return
   */
  @GetMapping("detail")
  public ResponseEntity<?> getNoticeList(@RequestParam long noticeSeq) {
    NoticeDetailDto noticeDetail = noticeService.getNoticeDetail(noticeSeq);
    return ResponseEntity.ok(noticeDetail);
  }
}
