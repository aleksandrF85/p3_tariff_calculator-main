FROM eclipse-temurin:21-jre

ENV JAVA_TOOL_OPTIONS='-agentlib:jdwp=transport=dt_socket,address=*:7001,server=y,suspend=n'

ARG JAR="app/target/app-1.0-SNAPSHOT.jar"

COPY $JAR /tariffCalculator.jar

ENTRYPOINT ["java","-jar"]
CMD ["tariffCalculator.jar"]