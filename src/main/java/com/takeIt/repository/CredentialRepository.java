package com.takeIt.repository;

import com.takeIt.entity.Account;
import com.takeIt.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, String> {
    Optional<Credential> findByAccessToken(String accessToken);
}
