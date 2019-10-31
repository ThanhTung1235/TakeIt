package com.takeIt.service.account;

import com.takeIt.entity.AccountInfo;
import com.takeIt.repository.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoServiceImp implements AccountInfoService{

    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Override
    public AccountInfo create(AccountInfo accountInfo) {
        return accountInfoRepository.save(accountInfo);
    }

    @Override
    public AccountInfo findByAccountId(long id) {
        return accountInfoRepository.findAccountInfoByAccount_id(id).orElse(null);
    }

    @Override
    public AccountInfo findByAccountInfoId(long id) {
        return accountInfoRepository.findAccountInfoById(id).orElse(null);
    }
}
