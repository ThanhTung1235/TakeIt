package com.takeIt.service.accountInfo;

import com.takeIt.entity.AccountInfo;
import com.takeIt.repository.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Override
    public AccountInfo store(AccountInfo accountInfo) {
        accountInfo.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        accountInfo.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        return accountInfoRepository.save(accountInfo);
    }

    //son.6-11
    @Override
    public AccountInfo findByAccountId(long id) {
        return accountInfoRepository.findAccountInfoByAccount_Id(id).orElse(null);
    }
    //son.6-11
    @Override
    public AccountInfo findByAccountInfoId(long id) {
        return accountInfoRepository.findAccountInfoById(id).orElse(null);
    }

//    @Override
//    public List<AccountInfo> getAll() {
//        return accountInfoRepository.findAll();
//    }

    @Override
    public AccountInfo update(long id, AccountInfo accountInfo) {
        return null;
    }

    @Override
    public AccountInfo getAccountInfo(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public AccountInfo getAccountInfoByAccountId(long id) {
        return accountInfoRepository.findByAccount_Id(id).orElse(null);
    }


}
