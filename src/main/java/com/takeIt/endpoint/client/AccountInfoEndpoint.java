package com.takeIt.endpoint.client;

import com.google.gson.Gson;
import com.takeIt.dto.InfoDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.accountInfo.AccountInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/_api/account-info")
public class AccountInfoEndpoint {
    Logger logger = LoggerFactory.getLogger(AccountInfoEndpoint.class);
    @Autowired
    AccountInfoService accountInfoService;


    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Object> saveProduct(@RequestBody AccountInfo accountInfo) {
        System.out.println(new Gson().toJson(accountInfo));
        accountInfoService.store(accountInfo);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Save success!")
                .setStatus(HttpStatus.CREATED.value())
                .addData(accountInfo).build(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}")
    public ResponseEntity<Object> updateAccountInfo(@PathVariable long id, @RequestBody AccountInfo accountInfo) {
        AccountInfo p = accountInfoService.getAccountInfo(id);
        if (p == null)
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("AccountInfo not found"), HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("update success!")
                    .addData(accountInfoService.update(id, accountInfo)), HttpStatus.OK);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/detail")
    public ResponseEntity<Object> getAccountInfo(HttpServletRequest request) {
        Account account = (Account) request.getAttribute("account");
        logger.info("Account info:" + account.getId());
        if (account == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Not found")
                    .build(),
                    HttpStatus.NOT_FOUND);
        }
        AccountInfo accountInfo = accountInfoService.getAccountInfo(account.getId());

        if (accountInfo == null)
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("accountInfo not found")
                    .build(),
                    HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Success")
                    .addData(new InfoDTO(accountInfo))
                    .build(),
                    HttpStatus.OK);
    }

}
