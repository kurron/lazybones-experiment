#!/bin/bash

# Docker and Docker Compose uses shared libraries so you need to have them already installed in the container.
# Bind mounting them won't work
CMD="docker run --rm \
                --add-host artifactory:192.168.1.89 \
                --add-host consul:192.168.1.89 \
                --add-host rabbitmq:192.168.1.40 \
                --env LANGUAGE=en_US \
                --env SPRING_RABBITMQ_HOST=rabbitmq \
                --env BUILD_DIR=/code/build \
                --env BINTRAY_USER_NAME=${BINTRAY_USER_NAME} \
                --env BINTRAY_API_KEY=${BINTRAY_API_KEY} \
                --hostname gradle \
                --interactive \
                --name gradle \
                --net host \
                --tty \
                --user root \
                --volume ${HOME}/.docker:/root/.docker:ro \
                --volume $(pwd):/code:rw \
                --volume /var/run/docker.sock:/var/run/docker.sock \
                --workdir /code \
                kurron/docker-azul-jdk-8:latest \
                ./gradlew  \
                --project-prop branch=master \
                --project-prop major=0 \
                --project-prop minor=0 \
                --project-prop patch=$(date +%s) \
                --project-prop runIntegrationTests=true \
                --project-prop publishArtifacts=true \
                --project-prop buildDir=/code/build \
                --project-dir=/code \
                --project-cache-dir=/tmp/gradle \
                --console=plain \
                --no-daemon \
                --no-search-upward \
                --stacktrace"

echo ${CMD}
${CMD}
