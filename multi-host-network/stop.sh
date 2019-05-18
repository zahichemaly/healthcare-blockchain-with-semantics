#!/bin/bash

echo "Forcefully killing remaining containers..."
#docker ps -aq | xargs docker stop
docker stop $(docker ps -aq)

echo "Removing chaincode images..."
DOCKER_IMAGE_IDS=$( docker images | awk '($1 ~ /.*.mycc.*/) {print $3}')

if [ -z "$DOCKER_IMAGE_IDS" -o "$DOCKER_IMAGE_IDS" == " " ]; then
  echo "---- No images available for deletion ----"
else
  echo "Deleting unwanted images"
  docker rmi -f $DOCKER_IMAGE_IDS
fi

