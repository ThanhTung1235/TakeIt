package com.takeIt.service.accountInfo;

import com.takeIt.entity.AccountInfo;

import java.util.List;

public interface AccountInfoService {
    AccountInfo store(AccountInfo accountInfo);

//    List<AccountInfo> getAll();

    AccountInfo update(long id, AccountInfo accountInfo);

    AccountInfo getAccountInfo(long id);

    boolean delete(long id);

    AccountInfo getAccountInfoByAccountId(long id);
}
