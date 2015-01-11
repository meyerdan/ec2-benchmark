#!/bin/bash

while (( "$#" )); do

  hostname=$1

  curl -sL -w "%{http_code} %{url_effective}\\n" -o curl-log/$hostname-metrics.log -H "Content-Type: application/json" -d "{}" http://${hostname}/benchmark-processes/metrics/stop

  shift
done


