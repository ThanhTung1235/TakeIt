package com.takeIt.service.transaction;

import com.takeIt.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    Transaction store(Transaction transaction);

    Page<Transaction> getAll(int page, int limit);
}
