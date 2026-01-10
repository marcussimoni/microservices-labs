#!/bin/bash

dockerComposes=("docker-compose-nginx" "docker-compose-dbs" "docker-compose-messages" "docker-compose-utilities" "docker-compose-apps")

for dockerCompose in "${dockerComposes[@]}"; do
    
    docker-compose -f "../docker/$dockerCompose.yaml" down 
    
done

docker network rm infra_network