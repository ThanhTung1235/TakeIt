package com.takeIt.repository;

import com.takeIt.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {
    Optional<AccountInfo> findByAccount_Id(long id);
}
