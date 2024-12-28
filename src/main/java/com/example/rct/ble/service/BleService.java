package com.example.rct.ble.service;

import com.example.rct.ble.model.BleDevice;
import com.example.rct.ble.repository.BleDeviceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BleService {

    private static final Logger logger = LoggerFactory.getLogger(BleService.class);

    @Value("${ble.scan.script.path}")
    private String scanScriptPath;

    @Value("${ble.connect.script.path}")
    private String connectScriptPath;

    private final BleDeviceRepository bleDeviceRepository;

    public BleService(BleDeviceRepository bleDeviceRepository) {
        this.bleDeviceRepository = bleDeviceRepository;
    }

    @Async
    public CompletableFuture<List<BleDevice>> scanBleDevicesAsync() {
        List<BleDevice> devices = new ArrayList<>();
        try {
            logger.info("BLE 스캔 시작");

            ProcessBuilder builder = new ProcessBuilder(
                    "powershell.exe",
                    "-ExecutionPolicy",
                    "Bypass",
                    "-File",
                    scanScriptPath
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line);
            }

            int exitCode = process.waitFor();
            logger.info("PowerShell 스크립트 종료 코드: {}", exitCode);

            if (exitCode != 0) {
                logger.error("PowerShell 스크립트가 비정상적으로 종료되었습니다.");
                return CompletableFuture.completedFuture(devices);
            }

            logger.debug("PowerShell 스크립트 출력: {}", jsonOutput);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonOutput.toString());
            if (root.isArray()) {
                for (JsonNode node : root) {
                    String name = node.get("name").asText();
                    String deviceId = node.get("deviceId").asText();
                    BleDevice device = new BleDevice(name, deviceId);
                    devices.add(device);
                }
            }

            logger.info("BLE 스캔 완료, {}개의 장치 발견", devices.size());

            if (!devices.isEmpty()) {
                bleDeviceRepository.saveAll(devices);
                logger.info("BLE 스캔 결과 MongoDB에 저장 완료");
            }

        } catch (Exception e) {
            logger.error("BLE 스캔 중 오류 발생", e);
        }
        return CompletableFuture.completedFuture(devices);
    }

    @Async
    public CompletableFuture<List<BleDevice>> getAllBleDevicesAsync() {
        List<BleDevice> devices = bleDeviceRepository.findAll();
        return CompletableFuture.completedFuture(devices);
    }

    @Async
    public CompletableFuture<Boolean> connectBleDeviceAsync(String deviceId) {
        if (!validateConnectScript()) {
            logger.warn("연결 스크립트 파일이 유효하지 않아 BLE 장치 연결을 중단합니다.");
            return CompletableFuture.completedFuture(false);
        }

        try {
            logger.info("BLE 장치에 연결 시도: {}", deviceId);

            ProcessBuilder builder = new ProcessBuilder(
                    "powershell.exe",
                    "-ExecutionPolicy",
                    "Bypass",
                    "-File",
                    connectScriptPath,
                    deviceId
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            logger.info("PowerShell 연결 스크립트 종료 코드: {}", exitCode);
            logger.debug("PowerShell 연결 스크립트 출력: {}", output);

            if (exitCode == 0) {
                logger.info("BLE 장치에 성공적으로 연결되었습니다: {}", deviceId);
                BleDevice device = bleDeviceRepository.findByDeviceId(deviceId);
                if (device != null) {
                    device.setConnected(true);
                    bleDeviceRepository.save(device);
                }
                return CompletableFuture.completedFuture(true);
            } else {
                logger.error("BLE 장치 연결에 실패했습니다: {}", deviceId);
                return CompletableFuture.completedFuture(false);
            }

        } catch (Exception e) {
            logger.error("BLE 장치 연결 중 오류 발생: {}", deviceId, e);
            return CompletableFuture.completedFuture(false);
        }
    }

    private boolean validateConnectScript() {
        try {
            logger.info("연결 스크립트 파일 검증을 시작합니다: {}", connectScriptPath);
            File connectScript = new File(connectScriptPath);

            if (!connectScript.exists()) {
                logger.warn("연결 스크립트 파일이 존재하지 않습니다: {}", connectScriptPath);
                return false;
            }

            if (!connectScript.isFile()) {
                logger.warn("연결 스크립트 경로가 파일이 아닙니다: {}", connectScriptPath);
                return false;
            }

            String content = new String(Files.readAllBytes(Paths.get(connectScriptPath)));
            if (!content.contains("BLE 장치 연결")) {
                logger.warn("연결 스크립트 파일 내용이 예상과 다릅니다: {}", connectScriptPath);
                return false;
            }

            logger.info("연결 스크립트 파일이 정상적으로 검증되었습니다.");
            return true;

        } catch (Exception e) {
            logger.error("연결 스크립트 파일 검증 중 오류 발생: {}", connectScriptPath, e);
            return false;
        }
    }
}