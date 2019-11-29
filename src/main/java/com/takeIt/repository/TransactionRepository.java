package com.takeIt.repository;

import com.takeIt.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByAccount_Id(long accountId, Pageable pageable);

    Page<Transaction> findByOwnerId(long owner, Pageable pageable);

    Optional<Transaction> findByExchangeRequest_Id(long exId);

}
