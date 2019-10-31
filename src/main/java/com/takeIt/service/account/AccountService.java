package com.takeIt.service.account;

import com.takeIt.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface AccountService {
    Account getAccount(long id);
    Account login(String username, String password);
    Account register(Account account);
    Account findByAccountId(long id);
    Page<Account> getAccountsWithPaginate(Specification specification, int page, int limit);
}
