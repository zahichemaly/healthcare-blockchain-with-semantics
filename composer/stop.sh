#!/bin/bash

set -e

echo "Stopping all containers"
docker-compose -f docker-compose.yml stop

echo "Removing all cards..."
rm -fr $HOME/.composer
composer card list
