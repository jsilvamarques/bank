FROM adoptopenjdk/openjdk11-openj9 AS base
COPY target/configserver-*.jar /app.jar

EXPOSE 8888

CMD java -jar /app.jar