## Healthcare System using Hyperledger Fabric (multi host)

### Prerequisites

You will need 2 images of any distro. We recommend using Ubuntu 16.04.

**[ PC1 ]** instruction to be executed on the first machine
**[ PC2 ]** instruction to be executed on the second machine.
**[ PC1 - PC2 ]** instruction to be executed on both machines

### Setting up the chaincode

Copy the folder `/chaincode` in this directory.

### Setting up the network

1. Generate the crypto materials **[ PC1 ]**

        ./generate.sh
        
2. Copy the `/crypto-config` folder to the other machine. **[ PC2 ]**

3. Initialize a docker swarm and join in as manager **[ PC1 ]**

        docker swarm init
        
        docker swarm join-token manager
        
    The output will be something similar to:
    
        docker swarm join — token SWMTKN-1–xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx-xxxxxxxxxxxxxxxxxxxxxxxxx IP:PORT
        
4. Copy the output and run it on the second machine **[ PC2 ]**
 
5. Create a network **[ PC1 ]**

        docker network create --attachable --driver overlay multi-net

### Starting the network

1. Start the Fabric containers on the first machine **[ PC1 ]**

        ./startHost1.sh

3. Start the Fabric containers on the second machine **[ PC2 ]**

        ./startHost2.sh

4. Start the CLI container **[ PC1 ]** 

        ./startCLI.sh

5. In the terminal of the container, create a channel, and make the peers join the channel **[ PC1 ]** 

        ./join.sh

6. Still in the terminal of the container, run this command to install and instanciate the chaincode (this may take a while) **[ PC1 ]** 

        ./install.sh
        
5. You should see a new docker image prefixed with `dev-` **[ PC1 ]** 

        docker images
        
### Invoking the chaincode functions

After successfully installing and instanciating the chaincode, you can start using the chaincode to query the ledger via the CLI container. Here are some commands you can try out:

* Creating a patient **[ PC1 ]** 
        
        peer chaincode invoke -n mycc -c '{"Args":["create_patient", "1000", "John", "Smith", "01/01/1995"]}' -C mychannel

* Getting a patient **[ PC1 ]** 

        peer chaincode invoke -n mycc -c '{"Args":["get_patient", "1000"]}' -C mychannel
        
* Getting all the patients **[ PC1 ]** 

        peer chaincode invoke -n mycc -c '{"Args":["get_all_patients"]}' -C mychannel
        
* Creating a prescription (**N.B: requires setting up GraphDB first!**) **[ PC1 ]** 

        peer chaincode invoke -n mycc -c '{"Args":["create_prescription", "1000", "Advil", "1TAB"]}' -C mychannel

* Creating an allergy **[ PC1 ]** 

        peer chaincode invoke -n mycc -c '{"Args":["create_allergy", "1000", "Wheats"]}' -C mychannel

### Stopping the network

1. Stop the running Fabric containers **[ PC1 - PC2 ]**

        ./stop.sh

2. If needed, you can completely remove the untagged containers, including the dev-containers **[ PC1 - PC2 ]**

		./teardown.sh
		
### Modifying and re-deploying the Java chaincode

1.  Stop **both** networks
2.  Modify the Java project using the IDE of your choice (It should be a gradle project)
3.  Copy the following files/folders
    * `/build` (if present)
    * `/src`
    * `build.settings`
    * `gradle.properties`
    
    to the `/chaincode` folder and replace all changes.

4. Open a terminal in the `/chaincode` directory and run

        gradle clean build shadowJar
        
    You can also do this using your IDE before copying the files.
    
5. Make sure the previous docker image containing your chaincode has been removed!
        
        docker images

    If not, remove it manually:
    
        docker images rm <image_name>
        
