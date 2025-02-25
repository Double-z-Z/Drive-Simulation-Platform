package com.example.service;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HBaseDataService {

    @Autowired
    private Connection hbaseConnection;

    public void uploadImageData(String rowKey, byte[] imageData) throws Exception {
        try (Table table = hbaseConnection.getTable(TableName.valueOf("image_data"))) {
            Put put = new Put(rowKey.getBytes());
            put.addColumn("data".getBytes(), "image".getBytes(), imageData);
            table.put(put);
        }
    }

    public void uploadLidarData(String rowKey, byte[] lidarData) throws Exception {
        try (Table table = hbaseConnection.getTable(TableName.valueOf("lidar_data"))) {
            Put put = new Put(rowKey.getBytes());
            put.addColumn("data".getBytes(), "lidar".getBytes(), lidarData);
            table.put(put);
        }
    }
} 