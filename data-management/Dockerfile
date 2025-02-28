# 使用 OpenJDK 17 作为基础镜像
FROM eclipse-temurin:17-jdk-alpine as builder

# 创建一个新用户和用户组
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 切换到新用户
USER appuser

# 设置工作目录
WORKDIR /app

# 第一阶段：下载 Gradle 发行版
COPY gradle/ ./gradle
COPY gradlew .
RUN ./gradlew --version

# 第二阶段： 构建项目
COPY data-management /app
RUN ./gradlew clean build -x test

# 使用 OpenJDK 17 运行时镜像
FROM eclipse-temurin:17-jre-alpine

# 复制构建的 JAR 文件到运行时镜像中
COPY --from=builder /app/build/libs/*.jar app.jar

# 暴露应用程序端口
EXPOSE 8080

# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"]