package com.example.demo.Controller;

import com.example.demo.DTO.TransactionDTO;
import com.example.demo.Entities.Transaction;
import com.example.demo.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000") // Allow Next.js to call this
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        // Hardcoding/Ensuring userId is present if not passed (for demo purposes)
        if (transactionDTO.getUserId() == null) {
            transactionDTO.setUserId(1L); // Default user
        }

        Transaction newTransaction = transactionService.addTransaction(transactionDTO);
        return ResponseEntity.ok(newTransaction);
    }
}
