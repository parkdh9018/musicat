package com.musicat.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



/*

예외 처리
예외 처리는 전역 예외 처리기를 사용해 처리
예외는 서비스 레이어에서 발생시키는 것이 좋습니다.


전역 예외 처리기
모든 예외를 처리하도록 한다.
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception exception) {
        // 적절한 HTTP 상태 코드와 함께 에러 메시지를 반환합니다.
        // 여기에서는 INTERNAL_SERVER_ERROR(500)를 사용하였지만, 상황에 따라 적절한 상태 코드를 선택하세요.
        // RunTimeException도 Exception 에 포함되므로 여기서 처리된다.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
