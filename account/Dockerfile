FROM adoptopenjdk/openjdk11-openj9 AS base
ARG application

COPY target/customer-*.jar /app.jar

EXPOSE 8080

ENV application ${application}
CMD java -jar /app/${application}.jar