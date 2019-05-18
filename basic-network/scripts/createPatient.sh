#!/bin/bash
peer chaincode invoke -n mycc -c '{"Args":["create_patient", "2000", "Mark Taylor"]}' -C mychannel
