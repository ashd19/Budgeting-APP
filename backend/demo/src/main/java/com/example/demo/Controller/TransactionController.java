package com.example.demo.Controller;

import com.example.demo.DTO.TransactionDTO;
import com.example.demo.Entities.Transaction;
import com.example.demo.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000") // Allow Next.js to call this
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        // Get authenticated user
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        com.example.demo.Entities.User currentUser = (com.example.demo.Entities.User) authentication.getPrincipal(); // Cast
                                                                                                                     // to
                                                                                                                     // our
                                                                                                                     // User
                                                                                                                     // entity

        transactionDTO.setUserId(currentUser.getId());

        Transaction newTransaction = transactionService.addTransaction(transactionDTO);
        return ResponseEntity.ok(newTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        // Get authenticated user
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        com.example.demo.Entities.User currentUser = (com.example.demo.Entities.User) authentication.getPrincipal();

        List<Transaction> transactions = transactionService.getTransactionsByUserId(currentUser.getId());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        // Get authenticated user
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        com.example.demo.Entities.User currentUser = (com.example.demo.Entities.User) authentication.getPrincipal();

        Transaction transaction = transactionService.getTransactionById(id);
        
        // Security check: make sure the transaction belongs to the current user
        if (transaction != null && transaction.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
