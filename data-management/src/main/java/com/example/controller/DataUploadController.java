package com.example.controller;

import com.example.service.HBaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class DataUploadController {

    @Autowired
    private HBaseDataService hBaseDataService;

    @PostMapping("/image")
    public String uploadImage(@RequestParam("rowKey") String rowKey, @RequestParam("file") MultipartFile file) {
        try {
            hBaseDataService.uploadImageData(rowKey, file.getBytes());
            return "Image uploaded successfully";
        } catch (Exception e) {
            return "Failed to upload image: " + e.getMessage();
        }
    }

    @PostMapping("/lidar")
    public String uploadLidar(@RequestParam("rowKey") String rowKey, @RequestParam("file") MultipartFile file) {
        try {
            hBaseDataService.uploadLidarData(rowKey, file.getBytes());
            return "Lidar data uploaded successfully";
        } catch (Exception e) {
            return "Failed to upload lidar data: " + e.getMessage();
        }
    }
} 