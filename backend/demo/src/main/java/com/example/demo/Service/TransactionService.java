package com.example.demo.Service;

import com.example.demo.DTO.TransactionDTO;
import com.example.demo.Entities.Category;
import com.example.demo.Entities.Transaction;
import com.example.demo.Entities.User;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // improved logic: Check if category exists -> Use it. If not -> Create it.
    public Transaction addTransaction(TransactionDTO dto) {

        // 1. Get the User involved
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Logic: "Find or Create" the Category
        // We check if "Food" already exists for this user.
        Optional<Category> existingCategory = categoryRepository.findByNameAndUser(dto.getCategoryName(), user);

        Category category;
        if (existingCategory.isPresent()) {
            // Use existing category
            category = existingCategory.get();
        } else {
            // CREATE NEW CATEGORY
            // This is where they "work hand in hand" automatically
            category = new Category(user, dto.getCategoryName(), dto.getType());
            categoryRepository.save(category);
        }

        // 3. Create the Transaction using the (found or newly created) Category
        Transaction transaction = new Transaction(
                user,
                category,
                dto.getAmount(),
                dto.getDescription(),
                dto.getDate());

        return transactionRepository.save(transaction);
    }

    // Get all transactions for a specific user
    public List<Transaction> getTransactionsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user);
    }

    // Get a specific transaction by ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElse(null);
    }
}
