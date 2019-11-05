package com.takeIt.endpoint.client;

import com.takeIt.dto.RequestDTO;
import com.takeIt.dto.TransactionDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.Gift;
import com.takeIt.entity.Transaction;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.accountInfo.AccountInfoService;
import com.takeIt.service.exchangeRequest.ExchangeRequestService;
import com.takeIt.service.gift.GiftService;
import com.takeIt.service.transaction.TransactionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/_api/transactions")
public class TransactionEndpoint {
    @Autowired
    TransactionService transactionService;
    @Autowired
    GiftService giftService;
    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    AccountService accountService;


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

    @RequestMapping(method = RequestMethod.GET, value = "/transactionConfirm")
    public ResponseEntity<Object> checkExpiration() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            List<Transaction> transactions = transactionService.transactions();
            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTimeInMillis()));
            for (Transaction transaction : transactions) {
                Date exDate = simpleDateFormat.parse(simpleDateFormat.format(transaction.getExpirationAt()));
                System.out.println("exDate: " + exDate + " currentDate: " + currentDate);
                if (currentDate.compareTo(exDate) == 0) {
                    Gift gift = giftService.getProduct(transaction.getGift().getId());
                    long accountId = gift.getAccount().getId();
                    AccountInfo accountInfo = accountInfoService.getAccountInfoByAccountId(accountId);
                    Account account = accountService.getAccount(transaction.getAccount().getId());

                    transactionService.sendMailConfirm(accountInfo.getEmail(), account.getUsername(), transaction.getId(), "", "https://media.shoptretho.com.vn/upload/image/product/20170721/xe-day-tre-em-gluck-b6-2017-2.jpg");
                    System.out.println("equals date");
                } else System.out.println("not equals date");

            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//http://localhost:8080/_api/transactions/exchangeConfirm/13?status=true   ||confirm exchange done
    @RequestMapping(value = "/exchangeConfirm/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> changeStatusTransaction(
            @PathVariable long id,
            @RequestParam(value = "status", required = false) boolean status) {
        Transaction transaction = transactionService.updateStatusTransaction(id, status);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .addData(null)
                .setMessage(" ").build(), HttpStatus.OK);
    }
}
