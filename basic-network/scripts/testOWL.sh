#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["test_owl", "1000"]}' -C mychannel
