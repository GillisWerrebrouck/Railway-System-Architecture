#!/bin/bash
# Randomly delete pods in a Kubernetes namespace.
# Pods with label 'chaos=immune' will be spared.
set -ex

: ${DELAY:=30}
: ${NAMESPACE:=default}

while true; do
  kubectl \
    --namespace "${NAMESPACE}" \
    -o 'jsonpath={.items[*].metadata.name}' \
    -l 'chaos!=immune' \
    get pods | \
      tr " " "\n" | \
      shuf | \
      head -n 1 |
      xargs -t --no-run-if-empty \
        kubectl --namespace "${NAMESPACE}" delete pod
  sleep "${DELAY}"
done
