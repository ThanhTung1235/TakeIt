package com.takeIt.endpoint.admin;

import com.takeIt.dto.AccountDTO;
import com.takeIt.dto.CredentialDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.Credential;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.accountInfo.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
//@RequestMapping(value = "/_api/admin/account")
public class AccountAdminEndpoint {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountInfoService accountInfoService;


    @RequestMapping(value = "/admin/account/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody Account account) {
        Credential credential = accountService.loginAdmin(account.getUsername(), account.getPassword());
        if (credential == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setMessage("Forbidden")
                    .setCode(HttpStatus.FORBIDDEN.value())
                    .build(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Welcome")
                .setStatus(HttpStatus.OK.value())
                .addData(new CredentialDTO(credential)).build(), HttpStatus.OK);
    }

    //get account
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

    //xóa account
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id){
        if (accountService.delete(id))
            return new ResponseEntity<>( new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Deleted!")
                    .build(),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("Delete fail!")
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //sửa account
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

}
