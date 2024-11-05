FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=tpi-contacts.jar
COPY ${JAR_FILE} tpi-contacts.jar
ENTRYPOINT ["java","-jar","tpi-contacts.jar"]