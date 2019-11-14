package com.takeIt.config;

import com.takeIt.entity.Credential;
import com.takeIt.service.credential.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    CredentialsService credentialsService;

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
        System.out.println("Username: " + user.getUsername());
        return user;
    }
}
