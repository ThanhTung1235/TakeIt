package com.takeIt.service.account;

import com.takeIt.entity.AccountInfo;

public interface AccountInfoService {
   AccountInfo create(AccountInfo accountInfo);
   AccountInfo findByAccountId(long id);
   AccountInfo findByAccountInfoId(long id);


}
