#!/bin/bash

echo -e "\nTesting authorization endpoint user:123456...\n"

curl -X PUT \
     -H 'Content-Type: application/json' \
     -d '{
         "userId": "123456",
         "messageId": "50.00 debit charge",
         "transactionAmount": {
             "amount": "50.00",
             "currency": "USD",
             "debitOrCredit": "DEBIT"
         }
     }' \
     http://localhost:8080/authorization/debit-charge

# Add a blank line
echo

echo -e "\nTesting authorization endpoint user:567...\n"

curl -X PUT \
     -H 'Content-Type: application/json' \
     -d '{
         "userId": "567",
         "messageId": "400.50 debit charge",
         "transactionAmount": {
             "amount": "400.50",
             "currency": "USD",
             "debitOrCredit": "DEBIT"
         }
     }' \
     http://localhost:8080/authorization/debit-charge

# Add a blank line
echo

echo -e "\nTesting authorization endpoint user:123456...\n"

curl -X PUT \
     -H 'Content-Type: application/json' \
     -d '{
         "userId": "123456",
         "messageId": "460.00 debit charge",
         "transactionAmount": {
             "amount": "460.00",
             "currency": "USD",
             "debitOrCredit": "DEBIT"
         }
     }' \
     http://localhost:8080/authorization/debit-charge

# Add a blank line
echo

echo -e "\nTesting authorization endpoint user:567...\n"

curl -X PUT \
     -H 'Content-Type: application/json' \
     -d '{
         "userId": "567",
         "messageId": "89.50 debit charge",
         "transactionAmount": {
             "amount": "89.50",
             "currency": "USD",
             "debitOrCredit": "DEBIT"
         }
     }' \
     http://localhost:8080/authorization/debit-charge

# Add a blank line
echo
