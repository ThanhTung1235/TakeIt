package com.takeIt.repository;

import com.takeIt.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountInfoRepository extends JpaRepository<AccountInfo,Long> {
    //tim accountInfo with account_id
    Optional<AccountInfo> findAccountInfoByAccount_Id(long id);
    Optional<AccountInfo> findAccountInfoById(long id);

    Optional<AccountInfo> findByAccount_Id(long id);
}

