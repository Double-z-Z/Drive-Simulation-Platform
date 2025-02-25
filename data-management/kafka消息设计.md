# Kafka 消息数据结构设计

## 主题名称
- **主题名称**：`vehicle-data`
  - 该主题用于接收来自汽车的场景数据。

## 消息数据结构
每条消息的结构如下：

```json
{
  "scene_token": "string",
  "vehicle_token": "string",
  "timestamp": "long",
  "camera_data": [ { 
    "channel": "front",
    "data_uri": "s3://bucket/path/to/image.jpg",
    "checksum": "sha256:abcd1234" 
    } ],
  "lidar_data": [ { 
    "channel": "top",
    "data_uri": "s3://bucket/path/to/point_cloud.bin",
    "checksum": "sha256:abcd1234" 
    } ]
}
```