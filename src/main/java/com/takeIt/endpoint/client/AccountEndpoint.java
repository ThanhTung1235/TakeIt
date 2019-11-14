package com.takeIt.endpoint.client;

import com.google.gson.Gson;
import com.takeIt.dto.AccountDTO;
import com.takeIt.dto.AccountInfoDTO;
import com.takeIt.dto.CredentialDTO;
import com.takeIt.dto.context.AccountInfoContext;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.Credential;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.accountInfo.AccountInfoService;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/_api/account")
public class AccountEndpoint {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountInfoService accountInfoService;

    @RequestMapping(value = "/_api/account", method = RequestMethod.GET)
    public ResponseEntity<Object> getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Specification specification = Specification.where(null);
        if (keyword != null && keyword.length() > 0) {
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("username", ":", keyword)))
                    .and(new GiftSpecification(new SearchCriteria("email", ":", keyword)))
                    .or(new GiftSpecification(new SearchCriteria("createdAt", ":", keyword)));
        }
        Page<Account> accounts = accountService.accountWithPaginate(specification, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Action success!")
                .addData(accounts.getContent().stream().map(x -> new AccountDTO(x)).collect(Collectors.toList()))
                .setPagination(new RESTPagination(page, limit, accounts.getTotalPages(), accounts.getTotalElements()))
                .build(),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable String id) {
        Account account = accountService.findByAccountId(Long.parseLong(id));
        if (account == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Not found")
                    .build(),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success")
                .addData(new AccountDTO(accountService.findByAccountId(Long.parseLong(id))))
                .build(),
                HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody AccountInfoContext accountInfoContext) {
        System.out.println(new Gson().toJson(accountInfoContext));
        Account account = new Account();
        account.setUsername(accountInfoContext.getUsername());
        account.setPassword(BCrypt.hashpw(accountInfoContext.getPassword(), BCrypt.gensalt()));
//        Account a = accountService.register(account, accountInfoContext);

        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Register success!")
                .addData(new AccountDTO(null)).build(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
        Credential credential = accountService.login(username, password);
        if (credential == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setMessage("Token not found")
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Welcome")
                .setStatus(HttpStatus.OK.value())
                .addData(new CredentialDTO(credential)).build(), HttpStatus.OK);
    }
}
