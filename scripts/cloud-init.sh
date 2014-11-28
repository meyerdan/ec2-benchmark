#!/bin/sh

curl -sSL https://get.docker.com/ | sh

usermod -aG docker ubuntu

docker run -d -p 8080:8080 -e DB_JDBC="jdbc:postgresql://camunda-postgres.XXX.eu-central-1.rds.amazonaws.com:5432/process_engine" -e DB_USERNAME="camunda" -e DB_PASSWORD="camunda" menski/ec2-benchmark
