FROM adoptopenjdk/openjdk11-openj9 AS base
COPY target/authorization-server-*.jar /app.jar

EXPOSE 9092

CMD java -jar /app.jar