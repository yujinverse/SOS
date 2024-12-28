package com.example.rct.ble.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileValidator {

    private static final Logger logger = LoggerFactory.getLogger(FileValidator.class);

    @Value("${ble.connect.script.path}")
    private String connectScriptPath;

    public boolean validateConnectScript() {
        logger.info("파일 검증을 시작합니다: {}", connectScriptPath);

        File connectScript = new File(connectScriptPath);

        if (!connectScript.exists()) {
            logger.warn("연결 스크립트 파일이 존재하지 않습니다: {}", connectScriptPath);
            return false;
        }

        if (!connectScript.isFile()) {
            logger.warn("연결 스크립트 경로가 파일이 아닙니다: {}", connectScriptPath);
            return false;
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(connectScriptPath)));
            if (!content.contains("BLE 연결")) { // 실제 검증 로직에 맞게 수정
                logger.warn("연결 스크립트 파일 내용이 예상과 다릅니다: {}", connectScriptPath);
                return false;
            } else {
                logger.info("연결 스크립트 파일이 정상적으로 검증되었습니다.");
                return true;
            }
        } catch (Exception e) {
            logger.error("연결 스크립트 파일을 읽는 중 오류가 발생했습니다: {}", connectScriptPath, e);
            return false;
        }
    }
}
