package com.takeIt.dto;

import com.takeIt.entity.ExchangeRequest;
import com.takeIt.util.ObjectUtil;

public class RequestDTO {
    private long id;
    private String message;
    private String accountName;
    private String giftName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public RequestDTO(ExchangeRequest exchangeRequest) {
        ObjectUtil.cloneObject(this, exchangeRequest);
        this.id = exchangeRequest.getId();
        this.accountName = exchangeRequest.getAccount().getUsername();
        this.message = exchangeRequest.getMessage();
        this.giftName = exchangeRequest.getGift().getName();
    }
}
