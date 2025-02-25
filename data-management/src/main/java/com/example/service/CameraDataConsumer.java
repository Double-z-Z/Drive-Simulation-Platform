package com.example.service;

import com.example.model.VehicleData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CameraDataConsumer {
    private final ObjectMapper objectMapper;

    public CameraDataConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topics.vehicle-data}", groupId = "${kafka.consumer.camera-group.group-id}")
    public void consumeCameraData(String message) {
        try {
            VehicleData vehicleData = objectMapper.readValue(message, VehicleData.class);
            // 处理相机数据
            log.info("Received camera data: {}", vehicleData.getCameraData());
        } catch (Exception e) {
            log.error("Error processing camera data", e);
        }
    }
} 