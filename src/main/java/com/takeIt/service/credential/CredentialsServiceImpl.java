package com.takeIt.service.credential;

import com.google.gson.Gson;
import com.takeIt.entity.Account;
import com.takeIt.entity.Credential;
import com.takeIt.repository.AccountRepository;
import com.takeIt.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialsServiceImpl implements CredentialsService {
    @Autowired
    CredentialRepository credentialRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public User findByToken(String token) {
        Optional<Credential> optional = credentialRepository.findByAccessToken(token);
        if (optional.isPresent()) {
            long id = optional.get().getAccount().getId();
            Optional<Account> account = accountRepository.findById(id);
            if (!account.isPresent()) return null;

            User user = new User(account.get().getUsername(), account.get().getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
            return user;
        }
        return null;
    }
}
