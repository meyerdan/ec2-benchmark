#!/bin/bash

TAG=${1:-latest}

docker build --force-rm=true --rm=true -t menski/ec2-benchmark:${TAG} .
