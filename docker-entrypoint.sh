#!/bin/sh

export OTEL_JAVAAGENT_CONFIGURATION_FILE=./otel-javaagent.properties

exec java -javaagent:./otel-javaagent.jar $1 -jar app.jar
