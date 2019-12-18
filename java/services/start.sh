#!/bin/sh
OPTS=-Xmx32m
PROFILE=dev
ARGS="${OPTS} -Dspring.profiles.active=${PROFILE}"

set -a SERVICES
SERVICES="eureka_server-infra.jar cloud_config-infra.jar api_gateway-infra.jar licensemanager-service.jar resourcemanager-system.jar workflow-service.jar workflow_designer-system.jar"

for SERVICE in ${SERVICES}
do
    java ${ARGS} -jar /opt/${SERVICE} &
    sleep 5
done

while /bin/true
do
    sleep 60
done

