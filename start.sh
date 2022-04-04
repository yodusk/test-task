#!/bin/bash

cd backend && mvn clean install && mvn package && cd ..

docker-compose down
docker-compose build
docker-compose up