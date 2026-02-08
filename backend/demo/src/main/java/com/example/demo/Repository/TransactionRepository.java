package com.example.demo.Repository;

import com.example.demo.Entities.Transaction;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find all transactions for a specific user
    List<Transaction> findByUser(User user);
}
