#!/bin/bash

TAG=${1:-latest}

docker build --force-rm=true --rm=true -t meyerdan/ec2-benchmark:${TAG} .
