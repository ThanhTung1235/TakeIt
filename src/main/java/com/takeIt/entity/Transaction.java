package com.takeIt.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Account account;
    @OneToOne
    private Gift gift;
    @OneToOne
    private ExchangeRequest exchangeRequest;
    private long ownerId;
    private int status;
    private long createdAt;
    private long updatedAt;
    private long expirationAt;

    public Transaction() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        this.createdAt = Calendar.getInstance().getTimeInMillis();
        this.updatedAt = Calendar.getInstance().getTimeInMillis();
        this.expirationAt = calendar.getTimeInMillis();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public ExchangeRequest getExchangeRequest() {
        return exchangeRequest;
    }

    public void setExchangeRequest(ExchangeRequest exchangeRequest) {
        this.exchangeRequest = exchangeRequest;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getExpirationAt() {
        return expirationAt;
    }

    public void setExpirationAt(long expirationAt) {
        this.expirationAt = expirationAt;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public enum Status {
        CANCEL_EXCHANGING(-1),
        IS_EXCHANGING(0),
        DONE(1);

        int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status findByValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            return null;
        }
    }
}
