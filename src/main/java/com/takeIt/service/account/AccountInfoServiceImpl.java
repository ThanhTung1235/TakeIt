package com.takeIt.service.account;

import com.takeIt.entity.AccountInfo;
import com.takeIt.repository.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Autowired
    AccountInfoRepository infoRepository;

    @Override
    public AccountInfo getAccountInfo(long id) {
        return infoRepository.findByAccount_Id(id).orElse(null);
    }
}
