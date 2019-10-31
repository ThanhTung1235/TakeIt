package com.takeIt.repository;

import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountById(long id);
}
