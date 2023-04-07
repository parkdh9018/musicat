package com.musicat.controller.user;


import com.musicat.data.dto.notice.NoticeModifyDto;
import com.musicat.data.dto.notice.NoticeWriteDto;
import com.musicat.data.dto.user.UserPageDto;
import com.musicat.service.user.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

  private final AdminService adminService;


  /**
   * 회원 전체 조회
   *
   * @param page
   * @param userNickname
   * @param isChattingBan
   * @param isBan
   * @return
   */
  @GetMapping("/user")
  public ResponseEntity<?> getUserList(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam String userNickname,
      @RequestParam String isChattingBan,
      @RequestParam String isBan) {

    Page<UserPageDto> userPageDto = adminService.getUserPage(userNickname, isChattingBan, isBan,
        page);
    return ResponseEntity.ok(userPageDto);
  }

  /**
   * 회원 채팅 금지 설정
   *
   * @param userSeqList
   * @return
   */
  @PutMapping("/user/chatting-ban")
  public ResponseEntity<?> userChattingBan(@RequestBody List<Long> userSeqList) {
    adminService.userChattingBan(userSeqList);
    return ResponseEntity.ok().build();
  }

  /**
   * 회원 채팅 금지 해제
   *
   * @param userSeqList
   * @return
   */
  @PutMapping("/user/not-chatting-ban")
  public ResponseEntity<?> userNotChattingBan(@RequestBody List<Long> userSeqList) {
    adminService.userNotChattingBan(userSeqList);
    return ResponseEntity.ok().build();
  }

  /**
   * 회원 활동 금지
   *
   * @param userSeqList
   * @return
   */
  @PutMapping("/user/ban")
  public ResponseEntity<?> userBan(@RequestBody List<Long> userSeqList) {
    adminService.userBan(userSeqList);
    return ResponseEntity.ok().build();
  }

  /**
   * 회원 활동 금지 해제
   *
   * @param userSeqList
   * @return
   */
  @PutMapping("/user/not-ban")
  public ResponseEntity<?> userNotBan(@RequestBody List<Long> userSeqList) {
    adminService.userNotBan(userSeqList);
    return ResponseEntity.ok().build();
  }

  /**
   * 공지사항 작성
   *
   * @param noticeWriteDto
   * @return
   */
  @PostMapping("/notice")
  public ResponseEntity<?> writeNotice(@RequestBody NoticeWriteDto noticeWriteDto) {
    adminService.writeNotice(noticeWriteDto);
    return ResponseEntity.ok().build();
  }

  /**
   * 공지사항 수정
   *
   * @param noticeModifyDto
   * @return 200, 404, 500
   */
  @PatchMapping("/notice")
  public ResponseEntity<?> modifyNotice(@RequestBody NoticeModifyDto noticeModifyDto) {
    adminService.modifyNotice(noticeModifyDto);
    return ResponseEntity.ok().build();
  }

  /**
   * 공지사항 삭제
   *
   * @param noticeSeq
   * @return 200, 404, 500
   */
  @DeleteMapping("/notice/{noticeSeq}")
  public ResponseEntity<?> deleteNotice(@PathVariable long noticeSeq) {
    adminService.deleteNotice(noticeSeq);
    return ResponseEntity.ok().build();
  }


}
