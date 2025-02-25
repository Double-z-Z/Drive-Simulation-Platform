package com.example.mock;

import com.example.model.VehicleData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class VehicleDataProducer {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.topics.vehicle-data}")
    private String vehicleDataTopic;

    @Value("${kafka.topics.data-uploaded}")
    private String dataUploadedTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 发送车辆数据的元数据到 Kafka 主题。
     * 
     * 该方法将车辆数据对象序列化为 JSON 字符串，并发送到指定的 Kafka 主题。
     */
    public void sendVehicleData(VehicleData vehicleData) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.LINGER_MS_CONFIG, 20);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384); // 16KB
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "zstd");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            String jsonData = objectMapper.writeValueAsString(vehicleData);
            ProducerRecord<String, String> record = new ProducerRecord<>(vehicleDataTopic, vehicleData.getVehicleToken(), jsonData);
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishDataUploadedEvent(VehicleData vehicleData) {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(getKafkaProperties())) {
            String eventData = objectMapper.writeValueAsString(vehicleData);
            ProducerRecord<String, String> eventRecord = new ProducerRecord<>(dataUploadedTopic, vehicleData.getVehicleToken(), eventData);
            producer.send(eventRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Properties getKafkaProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }
} 