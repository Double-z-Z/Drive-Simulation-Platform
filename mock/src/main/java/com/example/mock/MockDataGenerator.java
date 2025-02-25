package com.example.mock;

import com.example.model.VehicleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MockDataGenerator implements CommandLineRunner {

    @Autowired
    private VehicleDataProducer vehicleDataProducer;

    @Autowired
    private HdfsUploader hdfsUploader;

    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            VehicleData vehicleData = generateMockVehicleData();

            // 模拟传感器数据上传
            byte[] sensorData = generateSensorData();
            String tempUri = hdfsUploader.uploadToHdfs("/path/to/hdfs", sensorData);

            // 将临时 URI 添加到元数据
            vehicleData.setDataUri(tempUri);
            vehicleDataProducer.sendVehicleData(vehicleData);

            // 发布 DataUploaded 事件
            vehicleDataProducer.publishDataUploadedEvent(vehicleData);

            Thread.sleep(1000); // 每秒发送一条数据
        }
    }

    private VehicleData generateMockVehicleData() {
        VehicleData vehicleData = new VehicleData();
        vehicleData.setSceneToken("scene-" + random.nextInt(100));
        vehicleData.setVehicleToken("vehicle-" + random.nextInt(10));
        vehicleData.setTimestamp(System.currentTimeMillis());
        // 添加更多模拟数据生成逻辑
        return vehicleData;
    }

    private byte[] generateSensorData() {
        // 生成模拟传感器数据
        return new byte[1024]; // 示例数据
    }
} 