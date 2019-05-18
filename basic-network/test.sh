#!/bin/bash

NETWORK_DIR=${HOME}/basic-network/scripts

cd
cd $NETWORK_DIR
cat <<EOF >script_create_patient.sh
#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_patient", "$id" , "$firstName" , "$lastName" , "$dateofbirth"]}' -C mychannel
EOF
chmod 777 script_create_patient.sh
