#!/bin/bash

# Base URL
BASE_URL="http://localhost:8080/adsweb/api/v1"

echo "--- 0. Authenticate as ADMIN ---"
ADMIN_TOKEN=$(curl -s -X POST "$BASE_URL/authenticate" \
     -H "Content-Type: application/json" \
     -d '{"username": "admin", "password": "admin123"}' | jq -r '.token')

if [ "$ADMIN_TOKEN" == "null" ] || [ -z "$ADMIN_TOKEN" ]; then
  echo "Failed to get Admin token. Check if the server is running and credentials are correct."
  exit 1
fi
echo "Admin Token obtained."
echo -e "\n"

echo "--- 0. Authenticate as JOHN (User) ---"
JOHN_TOKEN=$(curl -s -X POST "$BASE_URL/authenticate" \
     -H "Content-Type: application/json" \
     -d '{"username": "john", "password": "john123"}' | jq -r '.token')

if [ "$JOHN_TOKEN" == "null" ] || [ -z "$JOHN_TOKEN" ]; then
  echo "Failed to get John token."
  exit 1
fi
echo "John Token obtained."
echo -e "\n"

echo "--- 1. [ADMIN] GET All Patients (Sorted by lastName ASC) ---"
curl -X GET "$BASE_URL/patients" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
     -H "Accept: application/json"
echo -e "\n"

echo "--- 2. [ADMIN] GET Patient by ID (Valid ID) ---"
curl -X GET "$BASE_URL/patients/1" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
     -H "Accept: application/json"
echo -e "\n"

echo "--- 3. [ADMIN] GET Patient by ID (Invalid ID - Exception Handling) ---"
curl -X GET "$BASE_URL/patients/999" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
     -H "Accept: application/json"
echo -e "\n"

echo "--- 4. [ADMIN] POST Register New Patient ---"
NEW_PATIENT_RESPONSE=$(curl -s -X POST "$BASE_URL/patients" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
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
}')
echo "$NEW_PATIENT_RESPONSE"
NEW_PATIENT_ID=$(echo "$NEW_PATIENT_RESPONSE" | jq -r '.patientId')
echo -e "\n"

echo "--- 5. [ADMIN] PUT Update Patient ---"
curl -X PUT "$BASE_URL/patient/1" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
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

echo "--- 6. [ADMIN] DELETE Patient ---"
curl -X DELETE "$BASE_URL/patient/$NEW_PATIENT_ID" \
     -H "Authorization: Bearer $ADMIN_TOKEN"
echo "Status: $? (Check output above or server logs for no-content response)"
echo -e "\n"

echo "--- 7. [USER: JOHN] GET Search Patients ---"
curl -X GET "$BASE_URL/patient/search/Bob" \
     -H "Authorization: Bearer $JOHN_TOKEN"
echo -e "\n"

echo "--- 8. [USER: JOHN] Attempt to DELETE (Should fail - 403 Forbidden) ---"
curl -i -X DELETE "$BASE_URL/patient/1" \
     -H "Authorization: Bearer $JOHN_TOKEN"
echo -e "\n"

echo "--- 9. [USER: JOHN] Attempt to POST (Should fail - 403 Forbidden) ---"
curl -i -X POST "$BASE_URL/patients" \
     -H "Authorization: Bearer $JOHN_TOKEN" \
     -H "Content-Type: application/json" \
     -d '{"firstName": "Unauthorized"}'
echo -e "\n"

echo "--- 10. GET All Addresses (ADMIN) ---"
curl -X GET "$BASE_URL/addresses" \
     -H "Authorization: Bearer $ADMIN_TOKEN" \
     -H "Accept: application/json"
echo -e "\n"
