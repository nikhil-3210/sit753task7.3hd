FROM jenkins/jenkins:lts

USER root

RUN apt-get update && apt-get install -y \
    maven \
    openjdk-17-jdk \
    curl \
    gnupg \
    unzip \
    docker.io \
    && curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

USER jenkins