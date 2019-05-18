#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_prescription", "1000" , "Provigil", "2pills/week"]}' -C mychannel
