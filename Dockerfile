
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/src-transport-0.0.1-SNAPSHOT.jar myapp.jar




EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "myapp.jar"]