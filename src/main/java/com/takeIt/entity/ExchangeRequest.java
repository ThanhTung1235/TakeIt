package com.takeIt.entity;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity
public class ExchangeRequest {
    @Id
    private long id;
    private String message;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Account account;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Gift gift;
    @OneToOne(mappedBy = "exchangeRequest", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Transaction transaction;
    private int status;

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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public enum Status {
        CONFIRMED(1), PENDING(0), CANCEL(-1);

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

    public static void main(String[] args) {
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setStatus(Status.CONFIRMED);
        System.out.println(new Gson().toJson(exchangeRequest.getStatus()));
    }
}
