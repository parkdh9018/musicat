package com.musicat.controller.user;


import com.musicat.data.dto.notice.NoticeWriteDto;
import com.musicat.data.dto.user.UserPageDto;
import com.musicat.data.dto.user.UserModifyBanDto;
import com.musicat.service.user.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;


    // 회원 전체 조회
    // 회원 닉네임, 채팅 금지 여부, 활동 금지 여부를 인수로 받는다.
    @GetMapping("/user")
    public ResponseEntity<?> getUserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String userNickname,
            @RequestParam String isChattingBan,
            @RequestParam String isBan) {

        Page<UserPageDto> userPageDto = adminService.getUserPage(userNickname, isChattingBan, isBan, page);
        return ResponseEntity.ok(userPageDto);
    }

    // 회원 채팅 금지 설정
    @PutMapping("/user/chatting-ban")
    public ResponseEntity<?> userChattingBan(@RequestBody List<Long> userSeqList) {
        adminService.userChattingBan(userSeqList);
        return ResponseEntity.ok().build();
    }

    // 회원 채팅 금지 해재
    @PutMapping("/user/not-chatting-ban")
    public ResponseEntity<?> userNotChattingBan(@RequestBody List<Long> userSeqList) {
        adminService.userNotChattingBan(userSeqList);
        return ResponseEntity.ok().build();
    }

    // 회원 활동 금지 설정
    @PutMapping("/user/ban")
    public ResponseEntity<?> userBan(@RequestBody List<Long> userSeqList) {
        adminService.userBan(userSeqList);
        return ResponseEntity.ok().build();
    }

    // 회원 활동 금지 해재
    @PutMapping("/user/not-ban")
    public ResponseEntity<?> userNotBan(@RequestBody List<Long> userSeqList) {
        adminService.userNotBan(userSeqList);
        return ResponseEntity.ok().build();
    }



    // 공지사항 작성
    @PostMapping("/notice")
    public ResponseEntity<?> writeNotice(@RequestBody NoticeWriteDto noticeWriteDto) {
        adminService.writeNotice(noticeWriteDto);
        return ResponseEntity.ok().build();
    }




}
