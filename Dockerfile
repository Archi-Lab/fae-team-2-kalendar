FROM maven:3.5.4-jdk-11-slim
WORKDIR /fae-team-2-kalendar
COPY . /fae-team-2-kalendar/
RUN mvn package

WORKDIR /fae-team-2-kalendar/java-app
RUN cp /fae-team-2-kalendar/target/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
