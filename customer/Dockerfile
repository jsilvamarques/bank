FROM adoptopenjdk/openjdk11-openj9 AS base
COPY target/customer-*.jar /app.jar

EXPOSE 8080

CMD java -jar /app.jar