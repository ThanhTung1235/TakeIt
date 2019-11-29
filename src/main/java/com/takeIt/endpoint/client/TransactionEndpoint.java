package com.takeIt.endpoint.client;

import com.takeIt.dto.TransactionDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.Gift;
import com.takeIt.entity.Transaction;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.accountInfo.AccountInfoService;
import com.takeIt.service.gift.GiftService;
import com.takeIt.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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


    @RequestMapping(method = RequestMethod.GET, value = "/receiver")
    public ResponseEntity<Object> getTransactionOfReceiver(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        try {
            Account account = (Account) request.getAttribute("account");
            if (account == null) {
                return new ResponseEntity<>(new RESTResponse.SimpleError()
                        .setCode(HttpStatus.FORBIDDEN.value())
                        .setMessage("FORBIDDEN !").build(), HttpStatus.FORBIDDEN);
            }
            Page<Transaction> transactions = transactionService.getAllReceiver(account.getId(), page, limit);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setPagination(new RESTPagination(page, limit, transactions.getTotalPages(), transactions.getTotalElements()))
                    .addData(transactions.getContent().stream().map(x -> new TransactionDTO(x)).collect(Collectors.toList()))
                    .setMessage("")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("server error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/owner")
    public ResponseEntity<Object> getTransactionOfOwner(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        try {
            Account account = (Account) request.getAttribute("account");
            if (account == null) {
                return new ResponseEntity<>(new RESTResponse.SimpleError()
                        .setCode(HttpStatus.FORBIDDEN.value())
                        .setMessage("FORBIDDEN !").build(), HttpStatus.FORBIDDEN);
            }
            Page<Transaction> transactions = transactionService.getAllOfOwner(account.getId(), page, limit);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setPagination(new RESTPagination(page, limit, transactions.getTotalPages(), transactions.getTotalElements()))
                    .addData(transactions.getContent().stream().map(x -> new TransactionDTO(x)).collect(Collectors.toList()))
                    .setMessage("")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("server error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //http://localhost:8080/_api/transactions/transactionConfirm "cron job 12am"
    @RequestMapping(method = RequestMethod.GET, value = "/transactionConfirm")
    public ResponseEntity<Object> checkExpiration() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            List<Transaction> transactions = transactionService.transactions();
            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTimeInMillis()));
            for (Transaction transaction : transactions) {
                Date exDate = simpleDateFormat.parse(simpleDateFormat.format(transaction.getExpirationAt()));
                if (transaction.getStatus() == 0) {
                    if (currentDate.compareTo(exDate) == 0) {
                        Gift gift = giftService.getProduct(transaction.getExchangeRequest().getGift().getId());
                        long accountId = gift.getAccount().getId();
                        AccountInfo accountInfo = accountInfoService.getAccountInfoByAccountId(accountId);
                        Account account = accountService.getAccount(transaction.getAccount().getId());
// 3 day send email confirm
                        transactionService.sendMailConfirm(accountInfo.getEmail(), account.getUsername(), transaction.getId(), "", "https://media.shoptretho.com.vn/upload/image/product/20170721/xe-day-tre-em-gluck-b6-2017-2.jpg");
                        System.out.println("equals date");
                    } else System.out.println("not equals date");
                }
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("ok").build(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("server error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/_api/transactions/exchangeConfirm/13?status=true   ||confirm exchange done
    @RequestMapping(value = "/exchangeConfirm/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> changeStatusTransaction(
            HttpServletRequest request,
            @PathVariable long id,
            @RequestParam(value = "status", required = false) boolean status) {
        try {
            Account account = (Account) request.getAttribute("account");
            Transaction transaction = transactionService.updateStatusTransaction(id, account.getId(), status);
            if (transaction != null) {
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("Món đồ đã được đến lấy").build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new RESTResponse.SimpleError()
                        .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setMessage("Lỗi hệ thống").build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("server error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
