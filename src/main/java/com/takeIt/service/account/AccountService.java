package com.takeIt.service.account;

import com.takeIt.dto.context.AccountInfoContext;
import com.takeIt.entity.Account;
import com.takeIt.entity.Credential;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;

public interface AccountService {
    Account getAccount(long id);

    Credential login(String username, String password);

    Account register(Account account, AccountInfoContext accountInfoContext);

    Account findByAccountId(long id);

    Page<Account> accountWithPaginate(Specification specification, int page, int limit);

    Page<Account> getAccountByAccountInfoId(long id, int status, int page, int limit);

    boolean delete(long id);
}
