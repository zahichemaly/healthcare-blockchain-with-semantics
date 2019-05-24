## Healthcare System using Hyperledger Composer

### Prerequisites

Make sure you installed all the necessary Fabric docker images by following the  [installation manual](https://hyperledger.github.io/composer/latest/installing/installing-index.html).
The `/bin` folder containing the binaries should be located in the root directory of the repository, outside `/composer`.

### Project structure

* `/chaincode` contains the Business Network Definition which will be deployed via `deploy.sh`. This script will also generate the `package.json` file.
* `crypto-config` contains all the cryptographic materials generated when running `start.sh`.

### Starting the network

1. Start the Fabric containers:

        ./start.sh

2. Create the Peer Administrator card

        ./createPeerAdminCard.sh

3. Finally, deploy the chaincode

        ./deploy.sh
		
    This will also create an Admin card that you can use to access the blockchain network.

### Stopping the network

1. Stop the running Fabric containers

        ./stop.sh

2. If needed, you can completely remove the untagged containers, including the dev-containers

		./teardown.sh
		
### Exposing the network to a REST API and generating the Angular application

1. Start the REST API server and navigate to [http://localhost:3000/explorer](http://localhost:3000/explorer)

        ./rest.sh

2. Generate the Angular 4 application using Yeoman and navigate to [http://localhost:4200/](http://localhost:4200/)

		yo hyperledger-composer:angular

        
### Other useful commands

*	Show all the Business Network Cards
		
		composer card list
		
*	Delete a specific card

		composer card delete <card_name>
		
*	Show all containers including hidden ones

		docker ps -aq
		
*	Show all docker images

		docker images
		
*	Manually delete a docker container

		docker container rm <container_name_or_id>
