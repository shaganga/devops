FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/hello-world-0.1.0.jar
COPY ${JAR_FILE} /tmp/hello-world-0.1.0.jar
ENTRYPOINT ["java","-jar","/tmp/hello-world-0.1.0.jar"]
