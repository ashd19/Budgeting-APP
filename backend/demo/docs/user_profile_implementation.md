# 🎯 User Profile & JWT Authentication Implementation

## Overview

This document explains the complete user profile functionality integrated with JWT authentication in the BudgetApp.

---

## 📁 Files Created

### 1. **Authentication Utilities** (`lib/auth.ts`)

Handles JWT token storage and retrieval:

- `setToken()` / `getToken()` - Manage JWT in localStorage
- `setUser()` / `getUser()` - Cache user data locally
- `isAuthenticated()` - Check if user is logged in
- `getAuthHeader()` - Generate Authorization header

### 2. **API Client** (`lib/api.ts`)

Centralized API communication:

- **Auth Endpoints**: `signup()`, `login()`, `getMe()`
- **Transaction Endpoints**: `create()`, `getAll()`, `getById()`
- **Auto Token Injection**: Adds Bearer token to all requests automatically
- **Auto Redirect on 401**: Redirects to login if token expires

### 3. **UserProfile Component** (`components/UserProfile.tsx`)

Displays user information:

- User avatar (first letter of name)
- Full name, email, member since date
- Logout button

### 4. **Login Page** (`app/auth/login/page.tsx`)

- Email & password form
- Error handling
- Auto-redirect to dashboard on success

### 5. **Signup Page** (`app/auth/signup/page.tsx`)

- Registration form with validation
- Password confirmation
- Auto-login after signup

---

## 🔄 Authentication Flow

### Registration Flow

```
User fills signup form
    ↓
POST /auth/signup (Backend creates user)
    ↓
Auto-login: POST /auth/login
    ↓
Store JWT token in localStorage
    ↓
GET /auth/me (Fetch user profile)
    ↓
Store user data in localStorage
    ↓
Redirect to dashboard
```

### Login Flow

```
User enters credentials
    ↓
POST /auth/login
    ↓
Receive JWT token
    ↓
Store token in localStorage
    ↓
GET /auth/me (Fetch user profile)
    ↓
Store user data in localStorage
    ↓
Redirect to dashboard
```

### Protected Route Access

```
User visits dashboard
    ↓
Check localStorage for token
    ↓
If NO token → Redirect to /auth/login
    ↓
If token exists → GET /auth/me
    ↓
If 401 error → Token expired, redirect to login
    ↓
If success → Show dashboard with user data
```

---

## 🎨 UI Features

### Dashboard Integration

1. **User Avatar Button**: Click to toggle profile view
2. **Dynamic Welcome Message**: "Welcome back, {user.name}"
3. **Profile Panel**: Shows on right side when avatar is clicked
4. **Transaction Creation**: Uses authenticated user automatically (no need to pass userId)

### Profile Panel

- Glassmorphism design
- User initials in gradient badge
- Email, name, join date
- Logout button

---

## 🔐 Security Features

1. **Token Auto-Injection**: All API calls automatically include Bearer token
2. **Auto-Logout on Expiry**: 401 errors trigger automatic logout and redirect
3. **Client-Side Route Protection**: Dashboard checks authentication before rendering
4. **Password Hashing**: BCrypt on backend (passwords never stored in plain text)
5. **Stateless Sessions**: JWT eliminates need for server-side session storage

---

## 🚀 How to Use

### Starting the Application

1. **Start Backend**:

   ```bash
   cd backend/demo
   ./mvnw spring-boot:run
   ```

2. **Start Frontend**:

   ```bash
   cd frontend
   npm run dev
   ```

3. **Register a New Account**:
   - Visit `http://localhost:3000/auth/signup`
   - Fill in: Name, Email, Password
   - Auto-redirects to dashboard

4. **Login**:
   - Visit `http://localhost:3000/auth/login`
   - Enter email and password
   - Redirects to dashboard

5. **View Profile**:
   - Click avatar in top-right
   - See profile details
   - Click "Sign Out" to logout

---

## 📊 Example: Creating a Transaction

**Old Way** (Manual userId):

```javascript
axios.post('/api/transactions', {
  userId: 1,  // ❌ Hardcoded, insecure
  amount: 50,
  ...
});
```

**New Way** (Automatic from JWT):

```javascript
transactionAPI.create({
  amount: 50,  // ✅ userId extracted from JWT automatically
  ...
});
```

The backend reads the JWT, extracts the user email, and associates the transaction with that user.

---

## 🛠️ Backend Changes Required

To fully support transaction retrieval, add this to `TransactionController.java`:

```java
@GetMapping
public ResponseEntity<List<Transaction>> getUserTransactions() {
    Authentication authentication = SecurityContextHolder
        .getContext()
        .getAuthentication();
    User currentUser = (User) authentication.getPrincipal();
    
    List<Transaction> transactions = transactionRepository
        .findByUserId(currentUser.getId());
    
    return ResponseEntity.ok(transactions);
}
```

And add to `TransactionRepository.java`:

```java
List<Transaction> findByUserId(Long userId);
```

---

## 📝 Summary

This implementation provides:

- ✅ Secure JWT authentication
- ✅ User profile display
- ✅ Protected routes
- ✅ Automatic token management
- ✅ Clean API architecture
- ✅ Beautiful UI/UX

Every transaction now automatically belongs to the logged-in user, making the app truly multi-user ready!
