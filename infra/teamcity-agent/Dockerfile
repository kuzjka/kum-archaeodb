FROM jetbrains/teamcity-agent

# Override default DNS for docker-in-docker containers
ADD daemon.json /etc/docker/daemon.json

USER root

# Add meters root CA certificate
ADD root-ca.crt /usr/local/share/ca-certificates/meters-root-ca.crt
RUN update-ca-certificates && \
    /opt/java/openjdk/bin/keytool -importcert -noprompt -cacerts -alias meters-root -file /usr/local/share/ca-certificates/meters-root-ca.crt -storepass changeit

# Install additional packages

# Temurin OpenJDK 17
ARG JDK17_URL=https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.7%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.7_7.tar.gz
ARG JDK17_CHECKSUM=e9458b38e97358850902c2936a1bb5f35f6cffc59da9fcd28c63eab8dbbfbc3b

RUN /bin/sh -c set -eux; \
    curl -LfsSo /tmp/openjdk17.tar.gz ${JDK17_URL}; \
    echo "${JDK17_CHECKSUM} */tmp/openjdk17.tar.gz" | sha256sum -c -; \
    mkdir -p /usr/lib/jvm/temurin-17-jdk-amd64; \
    cd /usr/lib/jvm/temurin-17-jdk-amd64; \
    tar -xf /tmp/openjdk17.tar.gz --strip-components=1; \
    rm -rf /tmp/openjdk17.tar.gz; \
    echo "Java version:"; \
    /usr/lib/jvm/temurin-17-jdk-amd64/bin/java --version; \
    /usr/lib/jvm/temurin-17-jdk-amd64/bin/keytool -importcert -noprompt -cacerts -alias meters-root -file /usr/local/share/ca-certificates/meters-root-ca.crt -storepass changeit;
	
# NodeJS 18
ARG NODE_DIST=node-v18.16.0-linux-x64.tar.xz
ARG NODE_URL=https://nodejs.org/dist/v18.16.0/${NODE_DIST}
ARG NODE_CHECKSUM_URL=https://nodejs.org/dist/v18.16.0/SHASUMS256.txt

RUN /bin/sh -c set -eux; \
    cd /tmp; \
    curl -LfsSo ${NODE_DIST} ${NODE_URL}; \
    curl -LfsSo SHASUMS256.txt ${NODE_CHECKSUM_URL}; \
    grep " ${NODE_DIST}\$" SHASUMS256.txt | sha256sum -c -; \
    tar -xJf ${NODE_DIST} -C /usr/local --strip-components=1 --no-same-owner; \
    rm SHASUMS256.txt ${NODE_DIST}; \
    ln -s /usr/local/bin/node /usr/local/bin/nodejs; \
    echo "Node version:"; \
    node --version; \
    echo "NPM version:"; \
    npm --version;

# Headless Chrome
RUN curl -sSL https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb https://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
	apt update && \
	apt install -y --no-install-recommends google-chrome-stable && \
	rm -rf /var/lib/apt/lists/*
	
USER buildagent
