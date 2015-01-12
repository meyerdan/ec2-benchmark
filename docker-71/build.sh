#!/bin/bash

TAG=${1:-7.1}

docker build --force-rm=true --rm=true -t meyerdan/ec2-benchmark:${TAG} .
