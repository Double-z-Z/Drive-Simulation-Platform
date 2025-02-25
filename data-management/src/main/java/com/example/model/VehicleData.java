package com.example.model;

import lombok.Data;
import java.util.List;

@Data
public class VehicleData {
    private String sceneToken;
    private String vehicleToken;
    private long timestamp;
    private List<CameraData> cameraData;
    private List<LidarData> lidarData;

    public String getDataUri() {
        return null;
    }

    public void setDataUri(String permUri) {

    }
}