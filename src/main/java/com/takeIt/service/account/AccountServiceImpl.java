package com.takeIt.service.account;

import com.takeIt.entity.Account;
import com.takeIt.repository.AccountRepository;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account login(String username, String password) {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(username);
        if (BCrypt.checkpw(password, optionalAccount.get().getPassword())) {
            return optionalAccount.orElse(null);
        }else {
            return null;

        }
    }

    @Override
    public Account register(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByAccountId(long id) {
        return accountRepository.findAccountById(id).orElse(null);
    }

    @Override
    public Page<Account> accountWithPaginate(Specification specification, int page, int limit) {
        return accountRepository.findAll(specification, PageRequest.of(page - 1, limit));
    }

    @Override
    public Page<Account> getAccountByAccountInfoId(long id, int status, int page, int limit) {
        return accountRepository.findByAccountInfo_IdAndStatus(id, status, PageRequest.of(page - 1, limit));
    }


}
