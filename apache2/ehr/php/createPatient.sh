#!/bin/bash

id=$1
firstName=$2
lastName=$3
dateofbirth=$4

export id
export firstName
export lastName
export dateofbirth

cd
cd /home/zahi/ehr-network/multi-host-network/scripts

cat <<EOF >script_create_patient.sh
#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_patient", "$id" , "$firstName" , "$lastName" , "$dateofbirth"]}' -C mychannel
EOF

chmod 777 script_create_patient.sh
container=$(sudo docker exec -i cli /bin/bash -c "./script_create_patient.sh" 2>&1)

if [[ $container = *[!\ ]* ]]; then
    output=$(echo $container | grep -o -P '".*')
    echo $output
else
    echo "NOT FOUND";
fi