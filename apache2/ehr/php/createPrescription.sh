#!/bin/bash

patientId=$1
drugName=$2
dosage=$3

export patientId
export drugName
export dosage

cd
cd /home/zahi/ehr-network/multi-host-network/scripts

cat <<EOF >script_create_prescription.sh
#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_prescription", "$patientId" , "$drugName", "$dosage"]}' -C mychannel
EOF
chmod 777 script_create_prescription.sh
container=$(sudo docker exec -i cli /bin/bash -c "./script_create_prescription.sh" 2>&1)

if [[ $container = *[!\ ]* ]]; then
    output=$(echo $container | grep -o -P '".*')
    echo $output
else
    echo "NOT FOUND";
fi

