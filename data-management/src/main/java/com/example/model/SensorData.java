package com.example.model;

import lombok.Data;
import java.util.Map;

@Data
public class SensorData {
    private String type;
    private Map<String, Object> data;
    private String status;
}