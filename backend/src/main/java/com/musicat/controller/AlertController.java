package com.musicat.controller;

import com.musicat.data.dto.alert.AlertInsertRequestDto;
import com.musicat.data.dto.alert.AlertModifyRequestDto;
import com.musicat.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
     * @return 200, 500
     */
    @PostMapping("")
    public ResponseEntity<Void> insertAlert(
            @RequestBody AlertInsertRequestDto alertInsertRequestDto) {
        alertService.insertAlert(alertInsertRequestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 알림 삭제
     *
     * @param alertSeq
     * @return
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
    public ResponseEntity<?> getAlertList(@PathVariable long userSeq) {
        return ResponseEntity.ok().body(alertService.getAlertList(userSeq));
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

    /**
     * 알림 조건부 검색
     *
     * @param condition
     * @param userSeq
     * @param query
     * @return
     */
    @GetMapping("/{condition}/{userSeq}")
    public ResponseEntity<?> getAlertListByCondition(@PathVariable String condition,
            @PathVariable long userSeq, @RequestParam String query) {
        return ResponseEntity.ok()
                .body(alertService.getAlertListByCondition(condition, userSeq, query));
    }

}
