#!/bin/bash
#
# Copyright IBM Corp All Rights Reserved
#
# SPDX-License-Identifier: Apache-2.0
#
# Exit on first error, print all commands.
#set -ev

echo "Shutting down the Docker containers that might be currently running..."
docker-compose -f docker-compose.yml stop

DOCKER_IMAGE_IDS=$(docker images | awk '($1 ~ /dev-peer.*.mycc.*/) {print $3}')

if [ -z "$DOCKER_IMAGE_IDS" -o "$DOCKER_IMAGE_IDS" == " " ]; then
  echo "---- No images available for deletion ----"
else
  echo "Deleting unwanted images"
  docker rmi -f $DOCKER_IMAGE_IDS
fi

