FROM openjdk:22-ea-17-jdk
COPY build/libs/BlogapiApplication-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
LABEL authors="gurus"
