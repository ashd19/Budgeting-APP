package com.example.demo.DTO;

import com.example.demo.Entities.Categorytypes;
import java.math.BigDecimal;
import java.time.LocalDate;

// This class represents the exact form data from your UI
// [Name] [Type] [Amount] [Add Button]
public class TransactionDTO {
    private Long userId;
    private String categoryName; // "Food"
    private Categorytypes type; // EXPENSE
    private BigDecimal amount; // 50.00
    private String description; // "Groceries at Walmart"
    private LocalDate date;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Categorytypes getType() {
        return type;
    }

    public void setType(Categorytypes type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
