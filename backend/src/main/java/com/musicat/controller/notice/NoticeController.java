package com.musicat.controller.notice;


import com.musicat.data.dto.notice.NoticeDetailDto;
import com.musicat.data.dto.notice.NoticeListDto;
import com.musicat.data.dto.user.UserListDto;
import com.musicat.service.NoticeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 작성 기준 최신순 3개 가져오기
    @GetMapping("/top3")
    public ResponseEntity<?> getNoticeTop3() {
        List<NoticeListDto> noticeTop3 = noticeService.getNoticeTop3();
        return ResponseEntity.ok(noticeTop3);
    }

    /**
     * 공지사항 10개 조회 (페이지네이션)
     * @param page
     * @return
     */
    @GetMapping("")
    public ResponseEntity<?> getNoticeList(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(noticeService.getNoticeList(page, query));

    }
    
    // 공지사항 상세 조회
    @GetMapping("detail")
    public ResponseEntity<?> getNoticeList(@RequestParam long noticeSeq) {
        NoticeDetailDto noticeDetail = noticeService.getNoticeDetail(noticeSeq);
        return ResponseEntity.ok(noticeDetail);
    }
}
