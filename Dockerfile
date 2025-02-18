FROM openjdk:21

WORKDIR /app

COPY target/civiceye-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]