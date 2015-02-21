#!/bin/bash

# This script will launch Magni using the test profile
$JAVA_HOME/bin/java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=dumponexit=true,defaultrecording=true,dumponexitpath=/tmp/magni.jfr -Dspring.profiles.active=test -jar build/libs/magni-0.0.0-SNAPSHOT.jar
