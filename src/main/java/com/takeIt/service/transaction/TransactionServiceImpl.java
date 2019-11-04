package com.takeIt.service.transaction;

import com.takeIt.entity.Transaction;
import com.takeIt.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction store(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<Transaction> getAll(int page, int limit) {
        return transactionRepository.findAll(PageRequest.of(page - 1, limit));
    }
}
