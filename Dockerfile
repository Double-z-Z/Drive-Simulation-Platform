# 使用 OpenJDK 17 作为基础镜像
FROM eclipse-temurin:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制构建的 JAR 文件到容器中
COPY build/libs/*.jar app.jar

# 暴露应用程序端口
EXPOSE 8080

# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"] 