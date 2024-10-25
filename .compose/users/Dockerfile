FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=tpi-users.jar
COPY ${JAR_FILE} tpi-users.jar
ENTRYPOINT ["java","-jar","tpi-users.jar"]