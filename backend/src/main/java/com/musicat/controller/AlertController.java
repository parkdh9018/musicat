package com.musicat.controller;

import com.musicat.data.dto.alert.AlertInsertRequestDto;
import com.musicat.service.AlertService;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class AlertController {

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

}
