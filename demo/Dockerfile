FROM openjdk:17-jdk-slim
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build -x test
ENTRYPOINT ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]