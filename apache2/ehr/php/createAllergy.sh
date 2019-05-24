#!/bin/bash

patientId=$1
allergyName=$2

export patientId
export allergyName

cd
cd /home/zahi/ehr-network/multi-host-network/scripts

cat <<EOF >script_create_allergy.sh
#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_allergy", "$patientId" , "$allergyName"]}' -C mychannel
EOF
chmod 777 script_create_allergy.sh
container=$(sudo docker exec -i cli /bin/bash -c "./script_create_allergy.sh" 2>&1)

if [[ $container = *[!\ ]* ]]; then
    output=$(echo $container | grep -o -P '".*')
    echo $output
else
    echo "NOT FOUND";
fi

