
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/src-transport-0.0.1-SNAPSHOT.jar myapp.jar


COPY certs/keystore.p12 keystore.p12

EXPOSE 8443

# Lancer l'application
ENTRYPOINT ["java", "-jar", "myapp.jar"]