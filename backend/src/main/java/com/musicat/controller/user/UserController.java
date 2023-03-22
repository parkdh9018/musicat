package com.musicat.controller.user;


import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.data.dto.user.UserModifyRequestDto;
import com.musicat.data.dto.user.UserMoneyLogDto;
import com.musicat.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    // 회원 상세정보 조회
    @GetMapping("")
    public ResponseEntity<?> getUserDetail(@RequestParam long userSeq)  {
        UserDetailDto userDetailDto = userService.getUserDetail(userSeq);
        return ResponseEntity.ok(userDetailDto);
    }

    // 회원 정보 수정
    @PutMapping("/nickname")
    public ResponseEntity<?> modifyUserNickname(@RequestBody UserModifyRequestDto userModifyRequestDto)  {
        userService.modifyUserNickname(userModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    // 회원 탈퇴
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@RequestParam long userSeq)  {
        userService.deleteUser(userSeq);
        return ResponseEntity.ok().build();
    }
    
    // 회원 재화 내역 상세 조회
    @GetMapping("/money")
    public ResponseEntity<?> getUserMoneyLogDetail(@RequestParam long moneyLogSeq)  {
        UserMoneyLogDto userMoneyLogDetail = userService.getUserMoneyLogDetail(moneyLogSeq);
        return ResponseEntity.ok(userMoneyLogDetail);
    }










}
