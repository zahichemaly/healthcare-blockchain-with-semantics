#!/bin/bash


P1=$(docker ps -q)
if [ "${P1}" != "" ]
then
  echo "Killing all running containers..."  &2> /dev/null
  docker kill ${P1}
else
  echo "No containers running..."
fi


