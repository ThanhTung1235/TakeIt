package com.takeIt.repository;

import com.takeIt.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountInfoRepository extends JpaRepository<AccountInfo,Long> {

}
