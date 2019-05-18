## EHR Business Network

This is the business network project (or chaincode) that can be deployed on Hyperledger Composer.

### Project structure

```
        ehr-network
        ├── lib
        │   └── script.js	---->	define the chaincode logic
        ├── models
        │   └── model.cto	---->	define the network entities: assets, participants, transactions, events...
        ├── package.json	---->	business network definition file
        ├── permissions.acl     ---->	define access control permissions to assets, transactions, participants...
        ├── queries.qry         ---->	define custom SQL queries that can be used by a REST API service
        └── README.md		

```

### Deploying the Business Network

There are two ways to deploy the Business Network on the blockchain:

1. Manually execute the following commands in order: (You can check the network_version in the `package.json`)

        composer archive create -t dir -n .

        composer network install --card PeerAdmin@hlfv1 --archiveFile ehr-network@<network_version>.bna

        composer network start --card PeerAdmin@hlfv1 --networkAdmin admin --networkAdminEnrollSecret adminpw --networkName ehr-network --networkVersion <network_version>

        composer card import --file admin@ehr-network.card

        composer network ping --card admin@ehr-network

2. Execute the following script in the root directory:

        ./deploy.sh

