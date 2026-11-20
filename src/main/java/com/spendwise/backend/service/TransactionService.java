package com.spendwise.backend.service;

import com.spendwise.backend.entity.Transaction;
import com.spendwise.backend.repository.TransactionRepository;
import com.spendwise.entity.User;
import com.spendwise.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // =====================================
    // TRANSACTION VALIDATION
    // =====================================

    private void validateTransaction(Transaction transaction) {

        if (transaction == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transaction details are required"
            );
        }

        if (transaction.getDescription() == null ||
                transaction.getDescription().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Description is required"
            );
        }

        if (transaction.getCategory() == null ||
                transaction.getCategory().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please select a category"
            );
        }

        if (transaction.getAmount() == null ||
                transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Amount must be greater than zero"
            );
        }

        if (transaction.getType() == null ||
                (!transaction.getType().equalsIgnoreCase("Income") &&
                 !transaction.getType().equalsIgnoreCase("Expense"))) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transaction type must be Income or Expense"
            );
        }

        if (transaction.getDate() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transaction date is required"
            );
        }

        // Clean up text values before saving
        transaction.setDescription(transaction.getDescription().trim());
        transaction.setCategory(transaction.getCategory().trim());

        // Keep transaction type consistent
        transaction.setType(
                transaction.getType().trim().equalsIgnoreCase("Income")
                        ? "Income"
                        : "Expense"
        );
    }

    // =====================================
    // ADD TRANSACTION FOR LOGGED-IN USER
    // =====================================

    public Transaction addTransaction(
            Transaction transaction, String email) {

        validateTransaction(transaction);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        // Assign transaction to authenticated user
        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    // =====================================
    // GET TRANSACTIONS OF LOGGED-IN USER
    // =====================================

    public List<Transaction> getAllTransactions(String email) {

        return transactionRepository.findAllByUser_Email(email);
    }

    // =====================================
    // GET TRANSACTION BY ID AND USER
    // =====================================

    public Optional<Transaction> getTransactionById(
            Long id, String email) {

        return transactionRepository.findByIdAndUser_Email(id, email);
    }

    // =====================================
    // UPDATE OWN TRANSACTION ONLY
    // =====================================

    public Transaction updateTransaction(
            Long id,
            Transaction updatedTransaction,
            String email) {

        validateTransaction(updatedTransaction);

        Transaction existingTransaction =
                transactionRepository.findByIdAndUser_Email(id, email)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Transaction not found"
                        ));

        existingTransaction.setDescription(
                updatedTransaction.getDescription());

        existingTransaction.setCategory(
                updatedTransaction.getCategory());

        existingTransaction.setAmount(
                updatedTransaction.getAmount());

        existingTransaction.setType(
                updatedTransaction.getType());

        existingTransaction.setDate(
                updatedTransaction.getDate());

        return transactionRepository.save(existingTransaction);
    }

    // =====================================
    // DELETE OWN TRANSACTION ONLY
    // =====================================

    public void deleteTransaction(Long id, String email) {

        Transaction transaction =
                transactionRepository.findByIdAndUser_Email(id, email)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Transaction not found"
                        ));

        transactionRepository.delete(transaction);
    }
}
