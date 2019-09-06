# Gralde
FROM gradle:jdk12 as builder

# Copy source files
COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

# Build with gradle
RUN gradle shadow


# PaperSpigot
FROM adoptopenjdk/openjdk11:alpine as paper

ARG MC_VERSION=Paper-1.14

WORKDIR /root

# Download and run MC_VERSION PaperClip, afterwards removing work directories
RUN wget -O PaperClip.jar https://papermc.io/ci/job/${MC_VERSION}/lastSuccessfulBuild/artifact/paperclip.jar && \
    java -jar PaperClip.jar && \
    rm -rf logs eula.txt server.properties


# server
FROM adoptopenjdk/openjdk12:alpine-jre

# Specify RAM
ENV MEMORY_JVM=512M
WORKDIR /minecraft
EXPOSE 25565

HEALTHCHECK --start-period=15s --interval=15s --timeout=5s CMD echo -e '\x0f\x00\x00\x09\x31\x32\x37\x2e\x30\x2e\x30\x2e\x31\x63\xdd\x01\x01\x00' | nc 127.0.0.1 25565 -w 1 | grep version

# Copy server.jar
COPY --from=paper /root/cache/patched_*.jar server.jar

# Copy plugin
COPY --from=builder /home/gradle/src/build/libs/*-all.jar plugins/

ENTRYPOINT /opt/java/openjdk/bin/java -Xms${MEMORY_JVM} -Xmx${MEMORY_JVM} -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=100 -XX:+DisableExplicitGC -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:G1MixedGCLiveThresholdPercent=35 -XX:+AlwaysPreTouch -XX:+ParallelRefProcEnabled -Dcom.mojang.eula.agree=true -jar server.jar
