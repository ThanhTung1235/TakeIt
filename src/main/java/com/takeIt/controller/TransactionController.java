package com.takeIt.controller;

import com.takeIt.dto.GiftDTO;
import com.takeIt.dto.TransactionDTO;
import com.takeIt.entity.Transaction;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/_api/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> search(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Page<Transaction> transactions = transactionService.getAll(page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setPagination(new RESTPagination(page, limit, transactions.getTotalPages(), transactions.getTotalElements()))
                .addData(transactions.getContent().stream().map(x -> new TransactionDTO(x)).collect(Collectors.toList()))
                .setMessage("")
                .build(), HttpStatus.OK);
    }
}
