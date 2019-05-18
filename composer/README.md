## Healthcare System using Hyperledger Composer

This project contains:
- A Hyperledger Composer Business Network for Healthcare System (i.e: chaincode)
- A Single organization blockchain deployement configuration
- A Multi-organization blockchain deployement configuration
- A REST API service to interact with the Business Network
- *in progress* A client side Angular 7 application that consumes the REST API service

### Prerequisites

To run Hyperledger Composer and Hyperledger Fabric, we recommend you have at least 4Gb of memory.

The following are prerequisites for installing the required development tools:

- **Operating Systems:** Ubuntu Linux 14.04 / 16.04 LTS (both 64-bit), or Mac OS 10.12
- **Docker Engine:** Version 17.03 or higher
- **Docker-Compose:** Version 1.8 or higher
- **Node: 8.9 or higher** *(note version 9 and higher is not supported)*
- **npm: v5.x**
- **git: 2.9.x or higher**
- **Python: 2.7.x**

1. You can run this command to fetch all the prerequisites above

        ./prereqs-ubuntu.sh

2. Install the CLI tools

        ./cli-tools.sh

3. Finally, install Hyperledger Fabric

        export FABRIC_VERSION=hlfv12

        ./downloadFabric.sh

### Running Fabric for the first time (Single organization)

1. Start Fabric

        ./startFabric.sh

2. Create a Peer Admin Card

        ./createPeerAdminCard.sh

3. Start the Composer Playground and navigate to [http://localhost:8080/login](http://localhost:8080/login)

        composer-playground

        
