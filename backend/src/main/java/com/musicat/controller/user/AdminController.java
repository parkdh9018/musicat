package com.musicat.controller.user;


import com.musicat.data.dto.user.UserListDto;
import com.musicat.service.user.UserService;
import com.musicat.util.ConstantUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final UserService userService;
    private final ConstantUtil constantUtil;

    // 모든 유저 정보 가져오기
    @GetMapping("/user")
    public ResponseEntity<?> getUserList(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<UserListDto> userListDtoPage = userService.getUserList(page, constantUtil.USER_PAGE_SIZE);
        return ResponseEntity.ok(userListDtoPage);
    }

    // 채팅금지, 활동금지 유저 정보 가져오기
    @GetMapping("/user/ban")
    public ResponseEntity<?> getUserBanList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "false") String isChattingBan,
            @RequestParam(defaultValue = "false") String isBan) {

        return null;

    }



}
