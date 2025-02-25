package com.example.service;

import com.example.model.VehicleData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LidarDataConsumer {
    private final ObjectMapper objectMapper;

    public LidarDataConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topics.vehicle-data}", groupId = "${kafka.consumer.lidar-group.group-id}")
    public void consumeLidarData(String message) {
        try {
            VehicleData vehicleData = objectMapper.readValue(message, VehicleData.class);
            // 处理激光雷达数据
            log.info("Received lidar data: {}", vehicleData.getLidarData());
        } catch (Exception e) {
            log.error("Error processing lidar data", e);
        }
    }
} 