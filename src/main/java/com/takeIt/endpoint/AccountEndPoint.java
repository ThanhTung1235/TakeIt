package com.takeIt.endpoint;

import com.takeIt.dto.AccountDTO;
import com.takeIt.dto.AccountInfoDTO;
import com.takeIt.dto.context.AccountInfoContext;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountInfoService;
import com.takeIt.service.account.AccountService;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import com.takeIt.util.JsonResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/_api/v1")
public class AccountEndPoint {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountInfoService accountInfoService;

    @RequestMapping(method = RequestMethod.GET)
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
        Page<Account> accounts = accountService.getAccountsWithPaginate(specification, page, limit);
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
            return new ResponseEntity<>(new JsonResponse()
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setMessage("Not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new JsonResponse()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success")
                .setData(new AccountDTO(accountService.findByAccountId(Long.parseLong(id)))),
                HttpStatus.OK);
    }


    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity<Object> storeAccount(@RequestBody AccountInfoContext accountInfoContext){
        AccountDTO accountDTO = accountInfoContext.getAccountDTO();
        AccountInfoDTO accountInfoDTO = accountInfoContext.getAccountInfoDTO();

        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(BCrypt.hashpw(accountDTO.getPassword(), BCrypt.gensalt()));
        account.setRole(Account.Roles.MEMBER);
        account.setStatus(Account.Status.ACTIVE);

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setFirstName(accountInfoDTO.getFirstName());
        accountInfo.setLastName(accountInfoDTO.getLastName());
        accountInfo.setEmail(accountInfoDTO.getEmail());
        accountInfo.setDob(accountInfoDTO.getDob());
        accountInfo.setAccount(account);
        return new ResponseEntity<>(new AccountInfoContext(
                new AccountDTO(accountService.register(account)),
                new AccountInfoDTO(accountInfoService.create(accountInfo))),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password){
        Account account = accountService.login(username, password);
        if (account == null){
            return null;
        }
        AccountInfo accountInfo = accountInfoService.findByAccountId(account.getId());
        return new ResponseEntity<>(new AccountInfoContext(
                new AccountDTO(account),
                new AccountInfoDTO(accountInfo)),
                HttpStatus.OK);
    }
}
