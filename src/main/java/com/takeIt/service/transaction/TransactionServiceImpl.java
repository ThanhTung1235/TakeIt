package com.takeIt.service.transaction;

import com.takeIt.entity.Transaction;
import com.takeIt.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction store(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
