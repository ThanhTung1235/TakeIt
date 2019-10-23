package com.takeIt.service.account;

import com.takeIt.entity.Account;
import com.takeIt.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
