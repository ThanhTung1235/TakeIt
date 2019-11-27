package com.takeIt.service.account;

import com.google.gson.Gson;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.Credential;
import com.takeIt.repository.AccountInfoRepository;
import com.takeIt.repository.AccountRepository;
import com.takeIt.repository.CredentialRepository;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import com.takeIt.util.StringUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountInfoRepository infoRepository;
    @Autowired
    CredentialRepository credentialRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Credential login(String username, String password) {
        try {
            Optional<Account> optionalAccount = accountRepository.findByUsername(username);
            Credential credential = null;
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                String passInput = StringUtil.hashPassword(password) + account.getSalt();
                if (passInput.equals(account.getPassword())) {
                    String token = UUID.randomUUID().toString();
                    credential = new Credential();
                    credential.setAccessToken(token);
                    credential.setAccount(account);
                    credentialRepository.save(credential);
                }
            }
            return credential;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Credential loginAdmin(String username, String password) {
        try {
            Optional<Account> optionalAccount = accountRepository.findByUsername(username);
            Credential credential = null;
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                if (account.getRole() == Account.Roles.ADMIN.getValue()) {
                    String passInput = StringUtil.hashPassword(password) + account.getSalt();
                    if (passInput.equals(account.getPassword())) {
                        String token = UUID.randomUUID().toString();
                        credential = new Credential();
                        credential.setAccessToken(token);
                        credential.setAccount(account);
                        credentialRepository.save(credential);
                    }
                }
            }
            return credential;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Account findByAccountId(long id) {
        return accountRepository.findAccountById(id).orElse(null);
    }

    @Override
    public Account register(Account account) {
        Optional<Account> optional = accountRepository.findByUsername(account.getUsername());
        if (optional.isPresent()) {
            logger.info("Ten tai khoan da co");
            return null;
        }else {
            return accountRepository.save(account);
        }
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
