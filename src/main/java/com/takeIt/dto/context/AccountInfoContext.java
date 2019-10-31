package com.takeIt.dto.context;

import com.takeIt.dto.AccountDTO;
import com.takeIt.dto.AccountInfoDTO;

public class AccountInfoContext {

    private AccountDTO accountDTO;
    private AccountInfoDTO accountInfoDTO;

    public AccountInfoContext() {
    }

    public AccountInfoContext(AccountDTO accountDTO, AccountInfoDTO accountInfoDTO){
        this.accountDTO = accountDTO;
        this.accountInfoDTO = accountInfoDTO;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public AccountInfoDTO getAccountInfoDTO() {
        return accountInfoDTO;
    }

    public void setAccountInfoDTO(AccountInfoDTO accountInfoDTO) {
        this.accountInfoDTO = accountInfoDTO;
    }
}
