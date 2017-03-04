#!/bin/bash

UNIXTIME=$(date +%s)
./gradlew -PrunIntegrationTests=false -PpublishArtifacts=true -Pbranch=master -Ppatch=${UNIXTIME}
