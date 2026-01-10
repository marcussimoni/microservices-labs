#!/bin/bash

projects=("payments" "email-sender" "user-management" "bookstore" "shipping" "healthcheck-app")

baseFolder="../../applications"

for project in "${projects[@]}"; do

    folder="$baseFolder/$project"
    cd $folder
    ./mvnw clean package -DskipTests

    docker build -t "$project-service:latest" . 

done