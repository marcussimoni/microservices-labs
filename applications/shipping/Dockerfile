FROM bellsoft/liberica-openjdk-alpine-musl:21.0.8 AS builder

WORKDIR builder

COPY target/*.jar app.jar

RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

RUN apk --no-cache add curl
RUN curl -L -o opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.20.1/opentelemetry-javaagent.jar

FROM bellsoft/liberica-openjdk-alpine-musl:21.0.8

WORKDIR /app

COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./
COPY --from=builder /builder/opentelemetry-javaagent.jar/ ./opentelemetry-javaagent.jar

EXPOSE 8084

ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-Dotel.instrumentation.micrometer.enabled=true", "-Xms128m", "-Xmx256m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "app.jar"]
