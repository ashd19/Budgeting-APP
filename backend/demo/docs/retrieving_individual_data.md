# 🔍 How to Retrieve and Render Individual Data (JWT Edition)

Now that you have JWT set up, retrieving data for a specific user (like "Ash") involves two main parts: the **Backend Endpoint** and the **Frontend Fetch**.

---

## 🎨 Part 1: The Backend (Spring Boot)

In a JWT system, you typically **don't** pass the `userId` in the URL for the logged-in user (e.g., `/users/1`). Instead, the user is identified by the **Token**. This is more secure because a user can't just change the number in the URL to see someone else's data.

### 1. The Controller Method

In your `AuthenticationController` or a `UserController`, you would create a method like this:

```java
@GetMapping("/me")
public ResponseEntity<User> getMyProfile() {
    // 1. Get the authenticated user from the Security Context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    // 2. The Principal is the "User" object we set in the Filter
    User currentUser = (User) authentication.getPrincipal();
    
    // 3. Return the user data
    return ResponseEntity.ok(currentUser);
}
```

### 2. The Repository Method

Since you already have `UserRepository.findByEmail(email)`, you can easily fetch full details if needed:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

---

## 🌐 Part 2: The Frontend (Next.js / React)

To render Ash's name on the screen, the frontend needs to send the "Golden Ticket" (JWT) in the header.

### 1. The Fetch Function

```javascript
const fetchUserData = async () => {
  const token = localStorage.getItem('token'); // Get the saved ticket

  const response = await fetch('http://localhost:8080/auth/me', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`, // Attach the ticket here!
      'Content-Type': 'application/json'
    }
  });

  if (response.ok) {
    const userData = await response.json();
    console.log("Found User:", userData.name); // This would be "Ash"
    return userData;
  }
};
```

### 2. The Rendering Component

```jsx
function ProfileHeader() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    fetchUserData().then(data => setUser(data));
  }, []);

  if (!user) return <div>Loading...</div>;

  return (
    <div className="profile-card">
      <h1>Welcome back, {user.name}!</h1> 
      <p>Email: {user.email}</p>
    </div>
  );
}
```

---

## 💡 Summary: Why this way?

1. **Security**: Ash can only see Ash's data. If he tried to change his name in the request, the **Signature** of the JWT would fail.
2. **Ease of Use**: The frontend doesn't need to track the `userId` (1, 2, 3...) manually; it just holds the token.
3. **Efficiency**: The backend knows exactly who is asking without searching the whole database by ID every time—it reads it right off the ticket!
