package com.takeIt.repository;

import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountById(long id);
//  Optional<Account> findByToken(String token);
}
