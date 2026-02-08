package com.example.demo.Repository;

import com.example.demo.Entities.Category;
import com.example.demo.Entities.Categorytypes;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find a category by name and user (so "Food" for User A is different from
    // "Food" for User B)
    Optional<Category> findByNameAndUser(String name, User user);

    // Find all categories for a specific user
    // This is useful for populating the dropdown in the UI
    // e.g. "Select Category: Food, Rent, Salary"
    java.util.List<Category> findByUser(User user);
}
