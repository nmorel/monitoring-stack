FROM dockerfile/java:oracle-java7

ADD target/metrics-example-1.0-SNAPSHOT.jar /metrics-example.jar

EXPOSE 8080

CMD [ "java", "-jar", "/metrics-example.jar" ]