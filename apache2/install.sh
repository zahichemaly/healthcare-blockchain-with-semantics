#!/bin/bash
echo " Installing Apache Server"
sudo apt-get update -y
sudo apt-get install apache2 -y
echo "Starting Apache Server"
sudo systemctl start apache2.service

echo "Installing PHP"
sudo apt-get install php -y

sudo systemctl enable apache2.service
systemctl restart apache2.service

