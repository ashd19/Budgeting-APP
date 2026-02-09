# 📚 Complete Documentation Index

Welcome to the BudgetApp documentation! Here's a guide to all available documentation.

---

## 📖 Documentation Files

### 1. **[JWT Flow Explanation](./jwt_flow_explanation.md)**

**Audience**: Beginners  
**Topics**:

- How JWT works in simple terms
- The "Movie Theater Ticket" analogy
- Stage-by-stage breakdown with examples using `ash@gmail.com`

---

### 2. **[Retrieving Individual Data](./retrieving_individual_data.md)**

**Audience**: Developers  
**Topics**:

- How to fetch user-specific data
- Backend controller methods
- Frontend API calls with JWT
- Example: Rendering user name on screen

---

### 3. **[User Profile Implementation](./user_profile_implementation.md)**

**Audience**: Developers  
**Topics**:

- Complete implementation guide
- File structure breakdown
- Authentication flow diagrams
- Security features
- Backend integration points

---

### 4. **[Quick Start Guide](./QUICK_START.md)**

**Audience**: Everyone  
**Topics**:

- How to run the application
- Step-by-step user signup
- Testing the implementation
- File structure overview

---

## 🗺️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        FRONTEND                              │
│  ┌────────────┐  ┌──────────────┐  ┌─────────────────┐     │
│  │  Login     │  │   Signup     │  │   Dashboard     │     │
│  │  Page      │  │   Page       │  │   (Protected)   │     │
│  └─────┬──────┘  └──────┬───────┘  └────────┬────────┘     │
│        │                 │                   │              │
│        └─────────────────┴───────────────────┘              │
│                          │                                  │
│                ┌─────────▼──────────┐                       │
│                │   API Client       │                       │
│                │  (lib/api.ts)      │                       │
│                └─────────┬──────────┘                       │
│                          │                                  │
│                ┌─────────▼──────────┐                       │
│                │  Auth Utilities    │                       │
│                │  (lib/auth.ts)     │                       │
│                │  - Token Storage   │                       │
│                │  - User Cache      │                       │
│                └────────────────────┘                       │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           │ HTTP + JWT Token
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                        BACKEND                               │
│  ┌───────────────────────────────────────────────────────┐  │
│  │          JwtAuthenticationFilter                      │  │
│  │   (Checks Bearer Token on Every Request)             │  │
│  └────────────────────┬──────────────────────────────────┘  │
│                       │                                      │
│         ┌─────────────┴──────────────┐                      │
│         │                            │                      │
│  ┌──────▼──────────┐      ┌─────────▼──────────┐           │
│  │  Auth           │      │  Transaction       │           │
│  │  Controller     │      │  Controller        │           │
│  │                 │      │                    │           │
│  │ /auth/signup    │      │ /api/transactions  │           │
│  │ /auth/login     │      │ (Protected)        │           │
│  │ /auth/me        │      └─────────┬──────────┘           │
│  └─────────────────┘                │                      │
│         │                            │                      │
│  ┌──────▼──────────┐      ┌─────────▼──────────┐           │
│  │  JwtService     │      │ TransactionService │           │
│  │  - Generate     │      │                    │           │
│  │  - Validate     │      └─────────┬──────────┘           │
│  │  - Parse        │                │                      │
│  └─────────────────┘                │                      │
│         │                            │                      │
│  ┌──────▼──────────────────────────┬─┘                      │
│  │                                 │                        │
│  │         JPA Repositories        │                        │
│  │  - UserRepository               │                        │
│  │  - TransactionRepository        │                        │
│  │  - CategoryRepository           │                        │
│  └─────────────────────────────────┘                        │
│                   │                                          │
│  ┌────────────────▼───────────────┐                         │
│  │      PostgreSQL Database       │                         │
│  │  - User_Table                  │                         │
│  │  - Transaction_Table           │                         │
│  │  - Category_Table              │                         │
│  └────────────────────────────────┘                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔑 Key Concepts

### JWT (JSON Web Token)

A secure token that contains user information. Think of it as a digital passport.

**Structure**:

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2hAZ21haWwuY29tIn0.Signature
    [Header]           [Payload: user email]      [Signature]
```

### Authentication Flow

1. User logs in → Server generates JWT
2. Frontend stores JWT in localStorage
3. Every API call includes: `Authorization: Bearer <JWT>`
4. Server validates JWT and extracts user info
5. Server returns data specific to that user

---

## 🛣️ User Journey

### New User

```
Visit localhost:3000
   ↓
Redirected to /auth/login (no token found)
   ↓
Click "Create account"
   ↓
Fill signup form → Submit
   ↓
Backend creates user + auto-login
   ↓
JWT stored in localStorage
   ↓
Redirect to dashboard
   ↓
See: "Welcome back, {Your Name}"
```

### Returning User

```
Visit localhost:3000
   ↓
Check localStorage for token
   ↓
Token found! GET /auth/me
   ↓
Receive user data
   ↓
Show dashboard immediately
```

### Creating a Transaction

```
User fills transaction form
   ↓
Click "Add Transaction"
   ↓
Frontend: transactionAPI.create(data)
   ↓
API automatically adds: Authorization: Bearer <token>
   ↓
Backend: JwtAuthenticationFilter extracts user from token
   ↓
Backend: Associates transaction with authenticated user
   ↓
No need to pass userId manually!
```

---

## 🎓 Learning Path

**Beginner → Advanced**:

1. Start with `jwt_flow_explanation.md` (understand the basics)
2. Read `QUICK_START.md` (get the app running)
3. Explore `retrieving_individual_data.md` (learn the API)
4. Study `user_profile_implementation.md` (understand full architecture)

---

## 🆘 Troubleshooting

### "Redirected to login immediately"

- Token may be expired or invalid
- Clear localStorage and signup again
- Check backend is running on port 8080

### "401 Unauthorized on API calls"

- Token might be missing or malformed
- Check Network tab: Is `Authorization` header present?
- Verify backend JWT secret matches

### "Cannot read property of undefined"

- User data might not be loaded yet
- Check the `loadingUser` state in dashboard

---

## 📞 Need Help?

Refer to the specific documentation file for detailed explanations:

- JWT basics → `jwt_flow_explanation.md`
- Data fetching → `retrieving_individual_data.md`  
- Full system → `user_profile_implementation.md`
- Getting started → `QUICK_START.md`

Happy coding! 🚀
