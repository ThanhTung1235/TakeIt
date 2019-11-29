package com.takeIt.dto;

import com.takeIt.entity.ExchangeRequest;
import com.takeIt.util.ObjectUtil;

public class RequestDTO {
    private long id;
    private String message;
    private String accountName;
    private String giftName;
    private String ownerName;
    private String status;
    private String thumbnail;
    private int numberOfStatus;

    public RequestDTO(ExchangeRequest exchangeRequest) {

        ObjectUtil.cloneObject(this, exchangeRequest);
        this.id = exchangeRequest.getId();
        this.accountName = exchangeRequest.getAccount().getUsername();
        this.message = exchangeRequest.getMessage();
        this.giftName = exchangeRequest.getGift().getName();
        this.numberOfStatus = exchangeRequest.getStatus();
        this.ownerName = exchangeRequest.getGift().getAccount().getUsername();
        String thumbnails = exchangeRequest.getGift().getThumbnail();
        String[] parts = thumbnails.split(",");
        this.thumbnail = parts[0];
        switch (exchangeRequest.getStatus()) {
            case -1:
                this.status = "Không xác nhận";
                break;
            case 0:
                this.status = "Đang chờ xác nhận";
                break;
            case 1:
                this.status = "Đồng ý trao đổi";
                break;
            default:
                break;
        }
    }

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfStatus() {
        return numberOfStatus;
    }

    public void setNumberOfStatus(int numberOfStatus) {
        this.numberOfStatus = numberOfStatus;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
