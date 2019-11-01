package com.takeIt.controller;

import com.takeIt.entity.Transaction;
import com.takeIt.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/_api/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getTransaction(@RequestBody Transaction transaction) {
        transactionService.store(transaction);
        return null;
    }
}
