#!/bin/bash

projects=("bookstore-commons" "payments" "email-sender" "user-management" "bookstore" "shipping" "healthcheck-app")

baseFolder="../../applications"

cd "$baseFolder/base-image"
docker build -t base-image:latest .

for project in "${projects[@]}"; do

    folder="$baseFolder/$project"
    cd $folder
    ./mvnw clean install -DskipTests

    docker build -t "$project-service:latest" . 

done