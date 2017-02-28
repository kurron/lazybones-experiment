#!/bin/bash

UNIXTIME=$(date +%s)
./gradlew -PrunIntegrationTests=true -Pbranch=master -Ppatch=${UNIXTIME}
