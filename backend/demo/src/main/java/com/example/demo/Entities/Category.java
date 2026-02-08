package com.example.demo.Entities;

import com.example.demo.Entities.User;
import jakarta.persistence.*;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "Category_Table")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @Enumerated(STRING)
    private Categorytypes type;

    public Category() {
    }

    public Category(User user, String name, Categorytypes type) {
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

    public Categorytypes getType() {
        return type;
    }

    public void setType(Categorytypes type) {
        this.type = type;
    }

}
