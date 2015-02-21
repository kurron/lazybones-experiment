#!/bin/bash

# This script will launch Magni using the default profile
$JAVA_HOME/bin/java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=dumponexit=true,defaultrecording=true,dumponexitpath=/tmp/magni.jfr -Dserver.port=8080 -jar build/libs/magni-0.0.0-SNAPSHOT.jar
