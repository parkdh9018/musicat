package com.musicat.controller.notice;


import com.musicat.data.dto.notice.NoticeDetailDto;
import com.musicat.data.dto.notice.NoticeListDto;
import com.musicat.service.NoticeService;
import lombok.RequiredArgsConstructor;
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

    // 공지사항 전체 조회
    @GetMapping("")
    public ResponseEntity<?> getNoticeList(@RequestParam int page) {

        return null;
    }
    
    // 공지사항 상세 조회
    @GetMapping("detail")
    public ResponseEntity<?> getNoticeList(@RequestParam long noticeSeq) {
        NoticeDetailDto noticeDetail = noticeService.getNoticeDetail(noticeSeq);
        return ResponseEntity.ok(noticeDetail);
    }
}
