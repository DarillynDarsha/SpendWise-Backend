package com.spendwise.backend.controller;

import com.spendwise.backend.entity.Transaction;
import com.spendwise.backend.service.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // =====================================
    // ADD TRANSACTION
    // =====================================

    @PostMapping
    public Transaction addTransaction(
            @RequestBody Transaction transaction,
            Authentication authentication) {

        String email = authentication.getName();

        return transactionService.addTransaction(transaction, email);
    }

    // =====================================
    // GET LOGGED-IN USER TRANSACTIONS
    // =====================================

    @GetMapping
    public List<Transaction> getAllTransactions(
            Authentication authentication) {

        String email = authentication.getName();

        return transactionService.getAllTransactions(email);
    }

    // =====================================
    // GET TRANSACTION BY ID
    // =====================================

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return transactionService.getTransactionById(id, email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =====================================
    // UPDATE TRANSACTION
    // =====================================

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                transactionService.updateTransaction(id, transaction, email)
        );
    }

    // =====================================
    // DELETE TRANSACTION
    // =====================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        transactionService.deleteTransaction(id, email);

        return ResponseEntity.noContent().build();
    }
}