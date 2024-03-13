FROM maven:3-openjdk-17-slim AS BUILD
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17 AS DEPLOY
WORKDIR /vk
COPY --from=BUILD /app/target/VKRestAPITask-0.0.1-SNAPSHOT.jar vk.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "vk.jar"]

