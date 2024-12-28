package com.example.rct.ble.controller;

import com.example.rct.ble.config.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileValidator fileValidator;

    @GetMapping("/validate-connect-script")
    public ResponseEntity<String> validateConnectScript() {
        boolean isValid = fileValidator.validateConnectScript();
        if (isValid) {
            return ResponseEntity.ok("연결 스크립트 파일이 정상적으로 검증되었습니다.");
        } else {
            return ResponseEntity.status(500).body("연결 스크립트 파일 검증에 실패했습니다.");
        }
    }
}
