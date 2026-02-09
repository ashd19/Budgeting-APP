# JWT Implementation Guide

## Overview

I have implemented JWT (JSON Web Token) authentication for your Spring Boot application. This allows users to register, login, and access protected resources securely.

## Changes Made

1. **Dependencies**: Updated `pom.xml` to use Spring Boot 3.4.0 (stable) and added `jjwt` dependencies for JWT handling.
2. **Security Configuration**:
    - Created `SecurityConfiguration` to configure the filter chain, disable CSRF, and enable CORS.
    - Created `ApplicationConfiguration` to define `AuthenticationManager`, `UserDetailsService`, and `PasswordEncoder` beans.
3. **JWT Logic**:
    - Created `JwtService` to generate and validate tokens.
    - Created `JwtAuthenticationFilter` to intercept requests and authenticate users via the `Authorization: Bearer <token>` header.
4. **Authentication**:
    - Created `AuthController` with `/auth/signup` and `/auth/login` endpoints.
    - Created `AuthService` to handle registration and login logic.
    - Updated `User` entity to implement `UserDetails`.
5. **Integration**:
    - Updated `TransactionController` to automatically use the authenticated user instead of a hardcoded ID.
    - Updated `DataInitializer` to create a demo user with a hashed password (`demo@example.com` / `password`).

## How to Run

1. Ensure you have a database running (PostgreSQL as configured in `application.yaml`, or switch to H2 if needed).
2. Run the application:

    ```bash
    ./mvnw spring-boot:run
    ```

## API Endpoints

### 1. Register a new user

**POST** `/auth/signup`

```json
{
  "email": "newuser@example.com",
  "password": "securepassword",
  "fullName": "New User"
}
```

### 2. Login

**POST** `/auth/login`

```json
{
  "email": "newuser@example.com", 
  "password": "securepassword"
}
```

**Response:**

```json
{
  "token": "dGhpcy...is...a...jwt...token",
  "expiresIn": 3600000
}
```

### 3. Get Current User (Authenticated)

**GET** `/auth/me`
**Headers:**
`Authorization`: `Bearer <your_token_here>`

**Response:**

```json
{
  "id": 1,
  "name": "Test User",
  "email": "testuser@example.com",
  "createdAt": "2023-11-20T10:00:00",
  "updatedAt": "2023-11-20T10:00:00",
  "authorities": [
    {
      "authority": "ROLE_USER"
    }
  ],
  "username": "testuser@example.com",
  "enabled": true,
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true
}
```

### 4. Add a Transaction (Authenticated)

**POST** `/api/transactions`
**Headers:**
`Authorization`: `Bearer <your_token_here>`

```json
{
  "amount": 50.0,
  "description": "Groceries",
  "date": "2023-10-27T10:00:00",
  "categoryName": "Food",
  "type": "EXPENSE"
}
```

*Note: You do not need to send `userId` anymore; it is inferred from the token.*
