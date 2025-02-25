package com.example.mock;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class HdfsUploader {

    private final Configuration configuration;

    public HdfsUploader() {
        configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://localhost:9000"); // 替换为您的 HDFS 地址
    }

    public String uploadToHdfs(String hdfsPath, byte[] data) throws IOException {
        FileSystem fs = FileSystem.get(configuration);
        Path path = new Path(hdfsPath);
        try (OutputStream os = fs.create(path)) {
            os.write(data);
        }
        return path.toString(); // 返回临时 URI
    }
} 