package com.example.rct.ble.exception;

import com.example.rct.ble.service.AsyncLoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final AsyncLoggingService asyncLoggingService;

    @Autowired
    public GlobalExceptionHandler(AsyncLoggingService asyncLoggingService) {
        this.asyncLoggingService = asyncLoggingService;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        asyncLoggingService.logException(ex, requestDescription);

        return new ResponseEntity<>("내부 서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
