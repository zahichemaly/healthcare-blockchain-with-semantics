#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_prescription", "1000" , "Provigil", "1pill"]}' -C mychannel
