#!/bin/bash

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
                --volume $(pwd):/code:ro \
                --volume /var/run/docker.sock:/var/run/docker.sock \
                --volume $(which docker):/usr/bin/docker \
                --volume $(which docker-compose):/usr/bin/docker-compose \
                --workdir /code \
                --user root \
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
                --project-cache-dir=/tmp/gradle \
                --stacktrace"

echo ${CMD}
${CMD}
