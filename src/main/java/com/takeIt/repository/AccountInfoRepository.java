package com.takeIt.repository;

import com.takeIt.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long>, JpaSpecificationExecutor<AccountInfo> {
    Optional<AccountInfo> findAccountInfoByAccount_Id(long id);
    Optional<AccountInfo> findAccountInfoById(long id);
}
