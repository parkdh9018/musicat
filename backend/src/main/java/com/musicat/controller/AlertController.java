package com.musicat.controller;

import com.musicat.data.dto.alert.request.AlertAllModifyRequestDto;
import com.musicat.data.dto.alert.request.AlertInsertRequestDto;
import com.musicat.data.dto.alert.request.AlertModifyRequestDto;
import com.musicat.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertController {

    // service 정의
    private final AlertService alertService;


    /**
     * 알람 등록
     *
     * @param alertInsertRequestDto
     * @return 201, 500
     */
    @PostMapping("")
    public ResponseEntity<?> insertAlert(
            @Validated @RequestBody AlertInsertRequestDto alertInsertRequestDto) {
        alertService.insertAlert(alertInsertRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("알림 등록에 성공하였습니다.");
    }

    /**
     * 알림 삭제
     *
     * @param alertSeq
     * @return 201, 404, 500
     */
    @DeleteMapping("/{alertSeq}")
    public ResponseEntity<Void> deleteAlert(@PathVariable long alertSeq) {
        alertService.deleteAlert(alertSeq);
        return ResponseEntity.ok().build();
    }

    /**
     * 알림 전체 조회 (userSeq 기준)
     *
     * @param userSeq
     * @return
     */
    @GetMapping("/{userSeq}")
    public ResponseEntity<?> getAlertList(@PathVariable long userSeq, @RequestParam(required = false, defaultValue = "") String query, @RequestParam(required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok().body(alertService.getAlertList(userSeq, query, page));
    }

    /**
     * 알림 상세 조회 (alerSeq 기준)
     *
     * @param alertSeq
     * @return
     */
    @GetMapping("detail/{alertSeq}")
    public ResponseEntity<?> getAlert(@PathVariable long alertSeq) {
        return ResponseEntity.ok().body(alertService.getAlert(alertSeq));
    }

    /**
     * 알림 수정 (읽음 처리)
     *
     * @param alertModifyRequestDto
     * @return
     */
    @PatchMapping("")
    public ResponseEntity<?> modifyAlert(@RequestBody AlertModifyRequestDto alertModifyRequestDto) {
        alertService.modifyAlert(alertModifyRequestDto);
        return ResponseEntity.ok().build();
    }

//    /**
//     * 알림 조건부 검색
//     *
//     * @param condition
//     * @param userSeq
//     * @param query
//     * @return
//     */
//    @GetMapping("/{condition}/{userSeq}")
//    public ResponseEntity<?> getAlertListByCondition(@PathVariable String condition,
//            @PathVariable long userSeq, @RequestParam(required = false, defaultValue = "") String query) {
//        return ResponseEntity.ok()
//                .body(alertService.getAlertListByCondition(condition, userSeq, query));
//    }

    /**
     * 안읽은 알림 개수 조회
     *
     * @param userSeq
     * @return
     */
    @GetMapping("/unread/{userSeq}")
    public ResponseEntity<?> getAlertCountByAlertIsReadFalse(@PathVariable long userSeq) {
        return ResponseEntity.ok().body(alertService.getAlertCountByAlertIsReadFalse(userSeq));
    }

    /**
     * 안읽은 알림 전체 읽음 처리
     *
     * @param userSeq
     * @param alertAllModifyRequestDto
     * @return
     */
    @PatchMapping("/unread/{userSeq}")
    public ResponseEntity<?> modifyAllAlert(@PathVariable long userSeq, @RequestBody
    AlertAllModifyRequestDto alertAllModifyRequestDto) {

        alertService.modifyAllAlert(userSeq, alertAllModifyRequestDto);
        return ResponseEntity.ok().build();
    }

}
