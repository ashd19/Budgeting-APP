#!/bin/bash

# Base URL
BASE_URL="http://localhost:8080"

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}Testing JWT Authentication Implementation${NC}\n"

# Generate unique email
TIMESTAMP=$(date +%s)
TEST_EMAIL="auto_user_${TIMESTAMP}@example.com"
echo "Using test email: $TEST_EMAIL"

# 1. Signup
echo "1. Registering new user..."
SIGNUP_Response=$(curl -s -X POST "$BASE_URL/auth/signup" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$TEST_EMAIL\",
    \"password\": \"password123\",
    \"fullName\": \"Auto Test User\"
  }")

echo "Response: $SIGNUP_Response"
echo -e "\n------------------------------------------------\n"

# 2. Login
echo "2. Logging in..."
# Capture headers and body
LOGIN_RESPONSE=$(curl -s -D - -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$TEST_EMAIL\",
    \"password\": \"password123\"
  }")

echo "Full Login Response:"
echo "$LOGIN_RESPONSE"

# Extract token
# Assuming response body is the last line or part of it
BODY=$(echo "$LOGIN_RESPONSE" | tail -n 1) # Simple assumption valid for small json responses
TOKEN=$(echo "$BODY" | grep -o '"token":"[^"]*"' | sed 's/"token":"//;s/"//')

if [ -z "$TOKEN" ]; then
    echo -e "${RED}Failed to extract token. Login failed.${NC}"
    # Wait, check if we got 403 or 401
    STATUS_CODE=$(echo "$LOGIN_RESPONSE" | grep "HTTP/" | awk '{print $2}')
    echo "Status Code: $STATUS_CODE"
    exit 1
fi

echo -e "${GREEN}Token extracted successfully.${NC}"
echo -e "\n------------------------------------------------\n"

# 3. Get Me
echo "3. Accessing protected endpoint /auth/me..."
ME_RESPONSE=$(curl -s -X GET "$BASE_URL/auth/me" \
  -H "Authorization: Bearer $TOKEN")

echo "Response: $ME_RESPONSE"
if [[ "$ME_RESPONSE" == *"testuser"* ]] || [[ "$ME_RESPONSE" == *"Auto Test User"* ]]; then
     echo -e "${GREEN}Successfully retrieved user details.${NC}"
else
     echo -e "${RED}Failed to verify user details.${NC}"
fi
echo -e "\n------------------------------------------------\n"

# 4. Create Transaction
echo "4. Creating a transaction..."
TRANSACTION_RESPONSE=$(curl -s -X POST "$BASE_URL/api/transactions" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "amount": 123.45,
    "description": "Test JWT Transaction",
    "date": "2023-11-20",
    "categoryName": "Testing",
    "type": "EXPENSE"
  }')

echo "Response: $TRANSACTION_RESPONSE"
echo -e "\n------------------------------------------------\n"

echo -e "${GREEN}Test script completed.${NC}"

echo -e "\n------------------------------------------------\n"
echo "5. Testing Invalid Token (Expecting Request to Fail)..."
INVALID_TOKEN="invalid_token_12345"
FAIL_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X GET "$BASE_URL/auth/me" \
  -H "Authorization: Bearer $INVALID_TOKEN")

if [ "$FAIL_RESPONSE" == "403" ] || [ "$FAIL_RESPONSE" == "401" ]; then
    echo -e "${GREEN}Success: Access denied with invalid token (HTTP $FAIL_RESPONSE).${NC}"
else
    echo -e "${RED}Failure: Expected 401/403 but got $FAIL_RESPONSE.${NC}"
fi
