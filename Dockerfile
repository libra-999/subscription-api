FROM openjdk:21-jdk-slim
WORKDIR /app
COPY artifacts/Subscriptionservice.jar app.jar
EXPOSE 5002
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
