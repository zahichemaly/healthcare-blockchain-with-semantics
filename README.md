# Healthcare blockchain with semantics
This project is about implementing a Healthcare System that takes advantage of semantics for a better patient treatment.
2 solutions are proposed:

- A secure Healthcare blockchain sytem using Hyperleder Composer
- A semantics-based Healthcare blockchain system using Hyperledger Fabric, Java and GraphDB.

## Prerequisites

Install prerequisites by running `./download.sh`
The script will install the following:

- **Docker Engine Version 17.03**
- **Docker-Compose Version 1.8**
- **Node 8.9**
- **npm v5.x**
- **git 2.9.x**
- **Python 2.7.x**

After that, download the **binaries** and the **Fabric 1.4.1** docker images by running `./setup.sh`

For more information, you can check this [link](https://hyperledger.github.io/composer/latest/installing/installing-prereqs.html).

## Project structure
- `/composer` contains the Hyperledger Composer project and a **Business Network Definition** in a Healthcare scenario
- `/basic-network` contains the Hyperledger Fabric project for a single host deployement
- `/multi-host-network` contains the Hyperledger Fabric project for deployement on 2 seperate hosts
- `/apache2` contains the setup and configuration files needed in order to run a Web application on an Apache Server with PHP enabled

