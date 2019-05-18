#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_patient", "" , "Panadol" , "2pills/day" , ""]}' -C mychannel
