#!/usr/bin/python
import MySQLdb

db = MySQLdb.connect(host="IP_ADDRESS",
                     user="USERNAME",
                     passwd="PASSWORD",
                     db="testing")
cur = db.cursor()
cur.execute("SELECT * from patient")
patients = []
with open('add_records.sh','a') as my_file:
          my_file.write("#!/bin/sh\n")
for row in cur.fetchall():
    with open('add_records.sh', 'a') as my_file:
        prefix = "peer chaincode invoke -n mycc -c '{\"Args\":[\"create_patient\", "
        i = 0
        while (i<len(row)-1):
            prefix = prefix + "\"" + str(row[i]) + "\", "
            i = i+1
        date = str(row[i])
        date=date[:10]
        prefix = prefix + "\"" + date + "\"]}' -C mychannel\n"
        print (prefix)
        my_file.write(prefix)

db.close()

