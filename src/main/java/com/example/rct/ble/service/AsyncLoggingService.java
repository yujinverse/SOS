package com.example.rct.ble.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncLoggingService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncLoggingService.class);

    @Async
    public void logException(Exception ex, String requestDescription) {
        logger.error("비동기 예외 처리 - 요청: {}, 예외: ", requestDescription, ex);
    }
}
