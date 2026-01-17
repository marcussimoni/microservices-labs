#!/bin/bash

param=$1

if [ "$param" = "build" ]; then
    sh build.sh
fi

sh stop.sh

network="infra_network"

echo "CREATING $network DOCKER NETWORK"
docker network create $network

dockerComposes=("docker-compose-dbs" "docker-compose-messages" "docker-compose-utilities" "docker-compose-apps" "docker-compose-nginx")

for dockerCompose in "${dockerComposes[@]}"; do
    
    docker-compose -f "../docker/$dockerCompose.yaml" up -d 
    
done

DASHBOARD_URL="http://localhost/dashboard"

# For Linux
if command -v xdg-open &> /dev/null; then
    xdg-open "$DASHBOARD_URL"
# For macOS
elif command -v open &> /dev/null; then
    open "$DASHBOARD_URL"
# Fallback for Windows (if running in WSL and want to open in Windows browser)
elif command -v cmd.exe &> /dev/null; then
    cmd.exe /c start "$DASHBOARD_URL"
fi