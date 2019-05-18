#!/bin/sh
#
# Copyright IBM Corp All Rights Reserved
#
# SPDX-License-Identifier: Apache-2.0

echo "Installing chaincode..."
peer chaincode install -l java -n mycc -v v1 -p /opt/gopath/src/github.com/chaincode/

echo "Instantiating chaincode..."
peer chaincode instantiate -o orderer.example.com:7050 -C mychannel -n mycc  -v v1 -c '{"Args":[]}' -P 'OR ("Org1MSP.member")'
