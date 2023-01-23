FROM openjdk:15-windowsservercore

LABEL authors="Armando Perez <aprezv@gmail.com>"

ADD target/api-*.jar /app/api.jar

WORKDIR /app

CMD ["java","-XX:MaxRAMPercentage=80.0", "-jar", "/app/api.jar"]
