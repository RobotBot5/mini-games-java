FROM gradle:8.7-jdk17 as builder
WORKDIR /app
COPY . /app/.
RUN gradle shadowJar -p /app

FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=builder /app/build/libs/minigames-0.1-all.jar /app/minigames.jar
VOLUME /tmp/.X11-unix:/tmp/.X11-unix
ENV DISPLAY=host.docker.internal:0.0
ENTRYPOINT ["java", "-jar", "/app/minigames.jar"]