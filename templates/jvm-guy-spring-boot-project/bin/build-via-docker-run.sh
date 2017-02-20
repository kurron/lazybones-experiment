#!/bin/bash

# Docker and Docker Compose uses shared libraries so you need to have them already installed in the container.
# Bind mounting them won't work
CMD="docker run --rm \
                --add-host artifactory:192.168.1.89 \
                --add-host consul:192.168.1.89 \
                --add-host rabbitmq:192.168.1.40 \
                --env LANGUAGE=en_US \
                --env SPRING_RABBITMQ_HOST=rabbitmq \
                --env BUILD_DIR=/tmp/gradle \
                --hostname gradle \
                --interactive \
                --name gradle \
                --net host \
                --tty \
                --user root \
                --volume $(pwd):/code:rw \
                --volume /var/run/docker.sock:/var/run/docker.sock \
                --workdir /code \
                kurron/docker-azul-jdk-8:latest \
                ./gradlew  \
                --project-prop runIntegrationTests=true \
                --project-prop publishArtifacts=true \
                --project-prop branch=master \
                --project-prop buildDir=/tmp/gradle \
                --project-dir=/code \
                --console=plain \
                --no-daemon \
                --no-search-upward \
                --stacktrace"

echo ${CMD}
${CMD}
