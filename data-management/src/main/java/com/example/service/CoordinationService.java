package com.example.service;

import com.example.model.VehicleData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CoordinationService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${kafka.topics.data-uploaded}", groupId = "${kafka.consumer.group-id}")
    public void handleDataUploadedEvent(ConsumerRecord<String, String> record) {
        try {
            VehicleData vehicleData = objectMapper.readValue(record.value(), VehicleData.class);
            // 检查临时 URI 并转换为永久 URI
            if (checkDataExists(vehicleData.getDataUri())) {
                String permUri = convertToPermanentUri(vehicleData.getDataUri());
                updateMetadataWithPermUri(vehicleData, permUri);
            } else {
                // 处理数据不存在的情况
                handleMissingData(vehicleData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDataExists(String tempUri) {
        // 检查对象存储中是否存在临时 URI
        return true; // 示例实现
    }

    private String convertToPermanentUri(String tempUri) {
        // 将临时 URI 转换为永久 URI
        return tempUri.replace("temp", "perm"); // 示例实现
    }

    private void updateMetadataWithPermUri(VehicleData vehicleData, String permUri) {
        // 更新元数据中的 URI
        vehicleData.setDataUri(permUri);
        // 可能需要将更新后的元数据写回 Kafka 或数据库
    }

    private void handleMissingData(VehicleData vehicleData) {
        // 处理数据不存在的情况，例如发送告警或重试
    }
} 