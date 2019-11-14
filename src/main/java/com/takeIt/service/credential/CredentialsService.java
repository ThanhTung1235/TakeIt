package com.takeIt.service.credential;

import com.takeIt.entity.Account;
import com.takeIt.entity.Credential;
import com.takeIt.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface CredentialsService {
    User findByToken(String token);
}
