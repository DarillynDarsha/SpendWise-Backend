package com.spendwise.backend.repository;

import com.spendwise.backend.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    // Fetch transactions belonging to a particular user
    List<Transaction> findAllByUser_Email(String email);

    // Find a transaction only if it belongs to that user
    Optional<Transaction> findByIdAndUser_Email(Long id, String email);

}