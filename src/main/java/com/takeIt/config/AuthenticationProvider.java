package com.takeIt.config;

import com.takeIt.entity.Account;
import com.takeIt.entity.Credential;
import com.takeIt.repository.AccountRepository;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.credential.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    CredentialsService credentialsService;
    @Autowired
    AccountRepository accountRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object credential = authentication.getCredentials();
        if (credential == null) {
            throw new UsernameNotFoundException("Credential not found!");
        }
        String accessToken = String.valueOf(credential);
        User user = credentialsService.findByToken(accessToken);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find user with authentication token=" + accessToken);
        }
        Optional<Account> accountOptional = accountRepository.findByUsername(user.getUsername());
        if (!accountOptional.isPresent()) return null;

        Account account = accountOptional.get();
        if (account == null) {
            System.out.println("not found account");
        }
        String role = "";
        if (account.getRole() == Account.Roles.MEMBER.getValue()) role = "MEMBER";
        if (account.getRole() == Account.Roles.ADMIN.getValue()) role = "ADMIN";
        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(role).build();
        return userDetails;
    }
}
