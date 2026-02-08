package com.example.demo.Entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
@Table(name = "User_Table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;
    
   @Column(name = "email")
   private String email;
   @Column(name = "passwordHash")
   private String passwordHash;
   @Column(name = "createdAt")
   private LocalDateTime createdAt;
   @Column(name = "updatedAt")
   private LocalDateTime updatedAt;
   
  public User(){}

   public User(String name, String email, String passwordHash) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
   }

   public Long getId() {
    return id;
   }

   public void setId(Long id) {
    this.id = id;
   }

   public String getName() {
    return name;
   }

   public void setName(String name) {
    this.name = name;
   }

   public String getEmail() {
    return email;
   }

   public void setEmail(String email) {
    this.email = email;
   }

   public String getPasswordHash() {
    return passwordHash;
   }

   public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
   }

   public LocalDateTime getCreatedAt() {
    return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
   }

   public LocalDateTime getUpdatedAt() {
    return updatedAt;
   }

   public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
   }



    
}