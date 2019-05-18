#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_allergy", "1000" , "Pollen"]}' -C mychannel
