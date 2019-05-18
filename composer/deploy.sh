#!/bin/bash

echo "Generating package..."
cat << EOF > chaincode/package.json
{
    "name": "ehr-network",
    "author": "author",
    "description": "EHR Network",
    "version": "0.0.1",
    "devDependencies": {
        "browserfs": "^1.2.0",
        "chai": "^3.5.0",
        "composer-admin": "latest",
        "composer-cli": "latest",
        "composer-client": "latest",
        "composer-connector-embedded": "latest",
        "eslint": "^3.6.1",
        "istanbul": "^0.4.5",
        "jsdoc": "^3.4.1",
        "mkdirp": "^0.5.1",
        "mocha": "^3.2.0",
        "moment": "^2.19.3"
    },
    "keywords": [],
    "license": "Apache 2.0",
    "repository": {
        "type": "e.g. git",
        "url": "URL"
    },
    "scripts": {
        "deploy": "./scripts/deploy.sh",
        "doc": "jsdoc --pedantic --recurse -c jsdoc.conf",
        "lint": "eslint .",
        "postlicchk": "npm run doc",
        "postlint": "npm run licchk",
        "prepublish": "mkdirp ./dist && composer archive create  --sourceType dir --sourceName . -a ./dist/unnamed-network.bna",
        "pretest": "npm run lint",
        "test": "mocha --recursive"
    }
}
EOF

echo "Creating Business Network Archive..."
composer archive create -t dir -n chaincode

echo "Installing network using PeerAdmin card..."
composer network install --card PeerAdmin@hlfv1 --archiveFile ehr-network@0.0.1.bna

echo "Starting network..."
composer network start --card PeerAdmin@hlfv1 --networkAdmin admin --networkAdminEnrollSecret adminpw --networkName ehr-network --networkVersion 0.0.1

echo "Importing Admin card"
composer card import --file admin@ehr-network.card

echo "Testing the network"
composer network ping --card admin@ehr-network


