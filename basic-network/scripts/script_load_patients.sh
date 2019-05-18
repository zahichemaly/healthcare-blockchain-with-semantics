#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["get_all_patients"]}' -C mychannel
