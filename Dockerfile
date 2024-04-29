#FROM gradle:8.7-jdk17 as builder
#WORKDIR /app
#COPY . /app/.
#RUN gradle shadowJar -p /app
#
#FROM eclipse-temurin:22-jre
#RUN apt-get update && \
#    apt-get install -y x11-apps
#WORKDIR /app
##--from=builder
#COPY /build/libs/minigames-0.1-all.jar /app/minigames.jar
#ENV DISPLAY=host.docker.internal:0.0
#ENTRYPOINT ["java", "-jar", "/app/minigames.jar"]

FROM ubuntu:20.04
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get -y install default-jre-headless && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY /build/libs/minigames-0.1-all.jar /app/minigames.jar

ENTRYPOINT ["java", "-jar", "/app/minigames.jar"]