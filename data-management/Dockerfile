FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "postgres:5432", "--", \
            "./wait-for-it.sh", "minio:9000", "--", \
            "java", "-jar", "app.jar"] 