package com.takeIt.dto;

import com.takeIt.entity.Transaction;
import com.takeIt.util.DateTimeUtil;

public class TransactionDTO {
    private long id;
    private long accountId;
    private String receiverName;
    private String ownerName;
    private long giftId;
    private String giftName;
    private long requestId;
    private String status;
    private String thumbnail;
    private int numberOfStatus;
    private String createdAt;
    private String updatedAt;
    private String expirationAt;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.receiverName = transaction.getAccount().getUsername();
        this.accountId = transaction.getAccount().getId();
        this.giftId = transaction.getExchangeRequest().getGift().getId();
        this.giftName = transaction.getExchangeRequest().getGift().getName();
        this.ownerName = transaction.getExchangeRequest().getGift().getAccount().getUsername();
        this.requestId = transaction.getExchangeRequest().getId();
        String thumbnails = transaction.getExchangeRequest().getGift().getThumbnail();
        String[] parts = thumbnails.split(",");
        this.thumbnail = parts[0];
        switch (transaction.getStatus()) {
            case -1:
                this.status = "Hủy giao dịch";
                break;
            case 0:
                this.status = "Đang giao dịch";
                break;
            case 1:
                this.status = "Hoàn thành";
                break;
            default:
                break;
        }
        this.numberOfStatus = transaction.getStatus();
        this.createdAt = DateTimeUtil.formatDateFromLong(transaction.getCreatedAt());
        this.updatedAt = DateTimeUtil.formatDateFromLong(transaction.getUpdatedAt());
        this.expirationAt = DateTimeUtil.formatDateFromLong(transaction.getExpirationAt());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getExpirationAt() {
        return expirationAt;
    }

    public void setExpirationAt(String expirationAt) {
        this.expirationAt = expirationAt;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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
