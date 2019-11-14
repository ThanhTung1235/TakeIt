package com.takeIt.service.account;

import com.google.gson.Gson;
import com.takeIt.dto.context.AccountInfoContext;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.Credential;
import com.takeIt.repository.AccountInfoRepository;
import com.takeIt.repository.AccountRepository;
import com.takeIt.repository.CredentialRepository;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountInfoRepository infoRepository;
    @Autowired
    CredentialRepository credentialRepository;

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Credential login(String username, String password) {
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(username, password);
        if (optionalAccount.isPresent()) {
            String token = UUID.randomUUID().toString();
            Account account = optionalAccount.get();

            Credential credential = new Credential();
            credential.setAccessToken(token);
            credential.setAccount(account);
            credentialRepository.save(credential);
            return credential;
        }
        return null;
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

    @Override
    public boolean delete(long id) {
        Optional<Account> p = accountRepository.findById(id);
        if (p.isPresent()) {
            Account g = p.get();
            g.setStatus(Account.Status.DELETE);
//            g.setDeletedAt(Calendar.getInstance().getTimeInMillis());
            accountRepository.save(g);
            return true;
        }
        return false;
    }

}
