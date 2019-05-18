#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_patient", "2000" , "Jon" , "Snow" , "1997-12-12"]}' -C mychannel
