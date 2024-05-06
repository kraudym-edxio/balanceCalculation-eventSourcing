#!/bin/bash

# Test load endpoint
echo -e "\nTesting load endpoint...\n"

curl -X PUT \
     -H 'Content-Type: application/json' \
     -d '{
         "userId": "123456",
         "messageId": "load-request",
         "transactionAmount": {
             "amount": "30.50",
             "currency": "USD",
             "debitOrCredit": "CREDIT"
         }
     }' \
     http://localhost:8080/load/load-request

# Add a blank line
echo
