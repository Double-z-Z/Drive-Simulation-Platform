package com.example.service;

import com.example.model.VehicleData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleDataConsumer {
    private final ObjectMapper objectMapper;

    public VehicleDataConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topics.vehicle-data}")
    public void consumeVehicleData(String message) {
        try {
            VehicleData vehicleData = objectMapper.readValue(message, VehicleData.class);
            // 处理车辆数据
            log.info("Received vehicle data: {}", vehicleData);
        } catch (Exception e) {
            log.error("Error processing vehicle data", e);
        }
    }
}