package com.takeIt.service.account;

import com.takeIt.entity.AccountInfo;
import com.takeIt.repository.AccountInfoRepository;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Autowired
    AccountInfoRepository accountInfoRepository;

    @Override
    public AccountInfo getAccountInfo(long id) {
        return accountInfoRepository.findAccountInfoByAccount_Id(id).orElse(null);
    }

    @Override
    public AccountInfo create(AccountInfo accountInfo) {
        return accountInfoRepository.save(accountInfo);
    }

    @Override
    public AccountInfo findByAccountId(long id) {
        return accountInfoRepository.findAccountInfoByAccount_Id(id).orElse(null);
    }

    @Override
    public AccountInfo findByAccountInfoId(long id) {
        return accountInfoRepository.findAccountInfoById(id).orElse(null);

    }

    @Override
    public String studentClass(AccountInfo accountInfo) {
        if (accountInfoRepository.save(accountInfo) != null) {
            return "Success";
        } else {
            return null;
        }

    }

    @Override
    public Page<AccountInfo> getAccountInfoWithPaginate(Specification specification, int page, int limit) {

        return accountInfoRepository.findAll(specification, PageRequest.of(page - 1, limit));

    }
}
