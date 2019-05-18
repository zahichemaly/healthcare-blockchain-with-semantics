#!/bin/bash
echo "Moving server folder to /var/www/html"
sudo cp ehr /var/www/html
sudo cd /var/www/html
echo "Applying root ownership on folder"
sudo chown root:root ehr
echo "Granting full access to files"
sudo chmod 777 -R ehr
echo "Changing group of executable files in order to be called via PHP"
sudo find ehr -type f -name '*.sh' -exec chgrp www-data {} \;


