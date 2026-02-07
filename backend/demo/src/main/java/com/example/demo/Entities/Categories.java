package com.example.demo.Entities;
import com.example.demo.Entities.User;
import jakarta.persistence.*;

@Entity
@Table(name = "Category_Table")
public class Categories {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String name;
    private String type; // INCOME , EXPENSE , SAVINGS 
    
    
    
    public Categories(User user, String name, String type) {
        this.user = user;
        this.name = name;
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    
    
}