package com.example.rct.ble.service;

import com.example.rct.ble.model.BleDevice;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class BleService {

    private static final String POWERSHELL_SCRIPT_PATH = "C:\\RCT\\ble-spring-server\\scan_ble.ps1"; // 실제 스크립트 경로로 변경

    public List<BleDevice> scanBleDevices() {
        List<BleDevice> devices = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "powershell.exe",
                    "-ExecutionPolicy",
                    "Bypass",
                    "-File",
                    POWERSHELL_SCRIPT_PATH
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line);
            }

            process.waitFor();

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            devices = mapper.readValue(jsonOutput.toString(), new TypeReference<List<BleDevice>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }
}
