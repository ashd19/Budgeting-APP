# 🔐 Understanding JWT Authentication Flow

This guide explains how JSON Web Tokens (JWT) work in your application in the simplest possible way.

## 🌟 The Scenario

- **Who:** Ash (`ash@gmail.com`)
- **Action:** Ash wants to log in with password `ash123` and then view his private dashboard.

---

## 🏎️ Stage 1: The Login (The "Ticket Counter")

Think of this like going to a movie theater. Before you enter the hall, you need a ticket.

1. **Request:** Ash sends his email (`ash@gmail.com`) and password (`ash123`) to the server at `/auth/login`.
2. **Verification:** The server checks the database.
    - Does the user exist? **Yes.**
    - Does the password match? (Server uses BCrypt to check the hash). **Yes.**
3. **Token Generation:** The server creates a "Golden Ticket" (the **JWT**). This ticket contains:
    - **Header:** Information about how it's signed (Algorithm).
    - **Payload:** Data about the user (e.g., `sub: ash@gmail.com`).
    - **Signature:** A unique seal made by combining the Header, Payload, and a **Secret Key** known only to the server.
4. **Response:** The server sends this JWT back to Ash. Ash's browser saves it.

---

## 🛡️ Stage 2: Accessing Protected Data (The "Security Guard")

Now Ash wants to see his Transactions. He doesn't want to send his password every time.

1. **Request:** Ash calls the endpoint `/api/transactions`.
2. **Attaching the Ticket:** He includes the JWT in the header of the request:
    - `Authorization: Bearer <The_JWT_Token>`
3. **The Filter (Security Gate):** Before reaching the actual code for transactions, the request hits the **JwtAuthenticationFilter**.
4. **Verification:** The server looks at the ticket:
    - **Is it expired?** No.
    - **Is the signature valid?** The server recreates the signature using its Secret Key. If they match, it knows the ticket hasn't been tampered with.
    - **Who is it?** The server extracts the email (`ash@gmail.com`) from the ticket.
5. **Setting the Context:** The server tells the rest of the application: *"Hey, for this specific request, the user is <ash@gmail.com>. Proceed!"*

---

## 📊 Stage 3: The Result (The Dashboard)

1. **Controller Action:** The `TransactionController` receives the request.
2. **User Identification:** It asks Spring Security, *"Who is currently logged in?"*. Spring Security says, *"It's <ash@gmail.com>"*.
3. **Data Fetch:** The controller fetches transactions only for `ash@gmail.com`.
4. **Response:** Ash sees his data!

---

## 📝 Summary of Roles

* **`JwtService`**: The "Ticket Maker & Checker" (Creates tokens, signs them, and reads them).
- **`JwtAuthenticationFilter`**: The "Security Guard" (Checks every incoming request for a valid ticket).
- **`SecurityConfiguration`**: The "Building Blueprint" (Defines which doors require a ticket and which don't).
- **`AuthenticationController`**: The "Front Desk" (Where you go to get your ticket in the first place).
