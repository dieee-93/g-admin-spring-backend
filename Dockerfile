FROM openjdk:17-jdk-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar gadmin.jar
ENTRYPOINT ["java", "-jar", "/gadmin.jar"]