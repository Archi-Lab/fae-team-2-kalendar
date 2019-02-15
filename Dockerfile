#Basis Image
FROM openjdk:12-jdk-alpine
#Port Freigeben für Docker
EXPOSE 8022
#Kopieren des Artefakts auf den Container
COPY target/microservice_kalendar-0.0.1-SNAPSHOT.jar app.jar
#Startpunkt für den Service festlegen
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","./app.jar"]