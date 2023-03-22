package com.musicat.controller.user;


import com.musicat.data.dto.notice.NoticeWriteDto;
import com.musicat.data.dto.user.UserListDto;
import com.musicat.data.dto.user.UserModifyBanDto;
import com.musicat.service.user.AdminService;
import com.musicat.service.user.UserService;
import com.musicat.util.ConstantUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    // 모든 유저 정보 가져오기
    @GetMapping("/user")
    public ResponseEntity<?> getUserList(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<UserListDto> userListDtoPage = adminService.getUserList(page);
        return ResponseEntity.ok(userListDtoPage);
    }

    // 채팅금지, 활동금지 유저 정보 가져오기
    @GetMapping("/user/ban")
    public ResponseEntity<?> getUserBanList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "false") String isChattingBan,
            @RequestParam(defaultValue = "false") String isBan) {

        Page<UserListDto> userBanListDtoPage = adminService.getUserBanList(page, isChattingBan, isBan);
        return ResponseEntity.ok(userBanListDtoPage);
    }

    // 회원 채팅 금지 설정
    @PutMapping("/user/chattingBan")
    public ResponseEntity<?> modifyUserChattingBan(@RequestBody UserModifyBanDto userModifyBanDto) {
        adminService.modifyUserChattingBan(userModifyBanDto);
        return ResponseEntity.ok().build();
    }

    // 회원 활동 금지 설정
    @PutMapping("/user/ban")
    public ResponseEntity<?> modifyUserBan(@RequestBody UserModifyBanDto userModifyBanDto) {
        adminService.modifyUserBan(userModifyBanDto);
        return ResponseEntity.ok().build();
    }


    // 공지사항 작성
    @PostMapping("/notice")
    public ResponseEntity<?> writeNotice(@RequestBody NoticeWriteDto noticeWriteDto) {
        adminService.writeNotice(noticeWriteDto);
        return ResponseEntity.ok().build();
    }




}
