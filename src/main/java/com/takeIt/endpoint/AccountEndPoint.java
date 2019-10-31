package com.takeIt.endpoint;

import com.takeIt.dto.AccountDTO;
import com.takeIt.dto.AccountInfoDTO;
import com.takeIt.dto.Context.AccountInfoContext;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.service.account.AccountInfoService;
import com.takeIt.service.account.AccountService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/_api/v1")
public class AccountEndPoint {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountInfoService accountInfoService;

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
