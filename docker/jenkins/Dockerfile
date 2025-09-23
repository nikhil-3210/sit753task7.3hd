FROM jenkins/jenkins:lts

# Switch to root to install packages
USER root

# Install Maven, JDK, Docker CLI, Trivy
RUN apt-get update && apt-get install -y \
    maven \
    openjdk-17-jdk \
    curl \
    gnupg \
    unzip \
    docker.io \
    && curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin

# Add Jenkins plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

USER jenkins