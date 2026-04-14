#!/bin/bash

# Base URL
BASE_URL="http://localhost:8080/adsweb/api/v1"

echo "--- 1. GET All Patients (Sorted by lastName ASC) ---"
curl -X GET "$http://localhost:8080/adsweb/api/v1/patients" -H "Accept: application/json"
echo -e "\n"

echo "--- 2. GET Patient by ID (Valid ID) ---"
curl -X GET "$http://localhost:8080/adsweb/api/v1/patients/1" -H "Accept: application/json"
echo -e "\n"

echo "--- 3. GET Patient by ID (Invalid ID - Exception Handling) ---"
curl -X GET "$http://localhost:8080/adsweb/api/v1/patients/999" -H "Accept: application/json"
echo -e "\n"

echo "--- 4. POST Register New Patient ---"
curl -X POST "$http://localhost:8080/adsweb/api/v1/patients" \
     -H "Content-Type: application/json" \
     -d '{
  "firstName": "Diana",
  "lastName": "Prince",
  "contactPhone": "555-5555",
  "email": "diana.p@amazon.com",
  "dateOfBirth": "1988-06-15",
  "outstandingBillBalance": 0.00,
  "address": {
    "street": "123 Wonder Way",
    "city": "Themyscira",
    "state": "Paradise Island",
    "zipCode": "00000"
  }
}'
echo -e "\n"

echo "--- 5. PUT Update Patient (Singular URI) ---"
curl -X PUT "$http://localhost:8080/adsweb/api/v1/patient/1" \
     -H "Content-Type: application/json" \
     -d '{
  "firstName": "Alice",
  "lastName": "Williams-Smith",
  "contactPhone": "555-8888",
  "email": "alice.smith@email.com",
  "dateOfBirth": "1990-05-14",
  "outstandingBillBalance": 50.00,
  "address": {
    "street": "123 Elm St",
    "city": "Austin",
    "state": "TX",
    "zipCode": "78701"
  }
}'
echo -e "\n"

echo "--- 6. DELETE Patient (Singular URI) ---"
curl -X DELETE "$BASE_URL/patient/3"
echo -e "\n"

echo "--- 7. GET Search Patients (Singular URI) ---"
curl -X GET "$BASE_URL/patient/search/Bob"
echo -e "\n"

echo "--- 8. GET All Addresses (Sorted by city ASC) ---"
curl -X GET "$BASE_URL/addresses" -H "Accept: application/json"
echo -e "\n"
