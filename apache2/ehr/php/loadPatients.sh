#!/bin/bash

cd
cd /home/zahi/ehr-network/multi-host-network/scripts
container=$(sudo docker exec -i cli /bin/bash -c "./script_load_patients.sh" 2>&1)

if [[ $container = *[!\ ]* ]]; then
    output=$(echo $container | grep -o -P '".*')
    echo $output
else
    echo "NOT FOUND";
fi

