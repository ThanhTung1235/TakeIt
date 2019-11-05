package com.takeIt.repository;

import com.takeIt.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Page<Account> findByAccountInfo_IdAndStatus(long id, int status, Pageable pageable);

    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountById(long id);
}
