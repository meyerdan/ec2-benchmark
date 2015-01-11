#!/bin/bash

PROC_KEY=$1
INSTANCES=$2

function start_process {
  local hostname=$1
  local proc_key=$2
  local instances=$3

  curl -X POST http://${hostname}/benchmark-processes/metrics/start

  for i in $(seq 1 $instances); do
    curl -H "Content-Type: application/json" -d "{}" http://${hostname}/engine-rest/process-definition/key/${proc_key}/start &
  done
}

shift 2


while (( "$#" )); do

  start_process $1 $PROC_KEY $INSTANCES &

  shift
done


