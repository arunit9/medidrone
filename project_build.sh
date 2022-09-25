#!/bin/bash
echo "starting  mvn package of the file-upload-api SpringBoot REST"
mvn package

echo "Finished mvn package"

echo "Starting docker build of the medidrone SpringBoot REST"
docker build -t arunit9/medidrone .

echo "Finished docker build of the medidrone SpringBoot REST"

echo "Starting docker stack"

docker-compose up

echo "Script execution successful"
