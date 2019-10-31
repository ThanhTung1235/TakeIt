package com.takeIt.service.account;

import com.takeIt.entity.AccountInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface AccountInfoService {
    AccountInfo getAccountInfo(long id);
    AccountInfo create(AccountInfo accountInfo);
    AccountInfo findByAccountId(long id);
    AccountInfo findByAccountInfoId(long id);
    String studentClass(AccountInfo accountInfo);
    Page<AccountInfo> getAccountInfoWithPaginate(Specification specification, int page, int limit);
}
