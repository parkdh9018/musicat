package com.musicat.controller;

import com.musicat.data.dto.alert.AlertInsertRequestDto;
import com.musicat.data.dto.alert.AlertModifyRequestDto;
import com.musicat.data.entity.Alert;
import com.musicat.service.AlertService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class AlertController {

    // service 정의
    private final AlertService alertService;


    /**
     * 알람 등록
     * @param alertInsertRequestDto
     * @return 200, 500
     */
    @PostMapping("")
    public ResponseEntity<Void> insertAlert(@RequestBody AlertInsertRequestDto alertInsertRequestDto) {
        try {
            alertService.insertAlert(alertInsertRequestDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 알림 삭제
     * @param alertSeq
     * @return
     */
    @DeleteMapping("/{alertSeq}")
    public ResponseEntity<Void> deleteAlert(@PathVariable long alertSeq) {
        try {
            alertService.deleteAlert(alertSeq);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 알림 전체 조회 (userSeq 기준)
     * @param userSeq
     * @return
     */
    @GetMapping("/{userSeq}")
    public ResponseEntity<?> getAlertList(@PathVariable long userSeq) {
        try {
            List<Alert> alertList = alertService.getAlertList(userSeq);
            return ResponseEntity.ok().body(alertList);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 알림 상세 조회 (alerSeq 기준)
     * @param alertSeq
     * @return
     */
    @GetMapping("detail/{alertSeq}")
    public ResponseEntity<?> getAlert(@PathVariable long alertSeq) {
        try {
            Alert alert = alertService.getAlert(alertSeq);
            return ResponseEntity.ok().body(alert);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 알림 수정 (읽음 처리)
     * @param alertModifyRequestDto
     * @return
     */
    @PatchMapping("")
    public ResponseEntity<?> modifyAlert(@RequestBody AlertModifyRequestDto alertModifyRequestDto) {
        try {
            alertService.modifyAlert(alertModifyRequestDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 알림 조건부 검색
     * @param condition
     * @param userSeq
     * @param query
     * @return
     */
    @GetMapping("/{condition}/{userSeq}")
    public ResponseEntity<?> getAlertListByCondition(@PathVariable int condition, @PathVariable long userSeq, @RequestParam String query) {
        try {
            List<Alert> alertList = alertService.getAlertListByCondition(condition, userSeq, query);
            return ResponseEntity.ok().body(alertList);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
