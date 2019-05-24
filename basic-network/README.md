## Healthcare System using Hyperledger Fabric (single host)

### Setting up the chaincode

Copy the folder `/chaincode` in this directory.

### Starting the network

1. Start the Fabric containers:

        ./start.sh

2. Open a terminal on the running CLI container:

        docker exec -it cli bash

3. Install the Java chaincode:

        peer chaincode install -l java -n mycc -v v1 -p /opt/gopath/src/github.com/chaincode/
		
4. Instanciate the chaincode (this may take a while):

        peer chaincode instantiate -o orderer.example.com:7050 -C mychannel -n mycc  -v v1 -c '{"Args":[]}' -P 'OR ("Org1MSP.member")'
        
5. You should see a new docker image prefixed with `dev-`

        docker images
        
### Invoking the chaincode functions

After successfully installing and instanciating the chaincode, you can start using the chaincode to query the ledger via the CLI container. Here are some commands you can try out:

* Creating a patient
        
        peer chaincode invoke -n mycc -c '{"Args":["create_patient", "1000", "John", "Smith", "01/01/1995"]}' -C mychannel

* Getting a patient

        peer chaincode invoke -n mycc -c '{"Args":["get_patient", "1000"]}' -C mychannel
        
* Getting all the patients

        peer chaincode invoke -n mycc -c '{"Args":["get_all_patients"]}' -C mychannel
        
* Creating a prescription (**N.B: requires setting up GraphDB first!**)

        peer chaincode invoke -n mycc -c '{"Args":["create_prescription", "1000", "Advil", "1TAB"]}' -C mychannel

* Creating an allergy

        peer chaincode invoke -n mycc -c '{"Args":["create_allergy", "1000", "Wheats"]}' -C mychannel

### Stopping the network

1. Stop the running Fabric containers

        ./stop.sh

2. If needed, you can completely remove the untagged containers, including the dev-containers

		./teardown.sh
		
### Modifying and re-deploying the Java chaincode

1.  Stop the network
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
        
