package com.takeIt.entity;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;
    @OneToOne
    private Gift gift;
    private int status;
    private int confirm_owner;

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

    public int getConfirm_owner() {
        return confirm_owner;
    }

    public void setConfirm_owner(int confirm_owner) {
        this.confirm_owner = confirm_owner;
    }

    public enum Status {
        CANCEL_EXCHANGING(-2),
        CANCEL(-1),
        PENDING_CONFIRM(0),
        PENDING(1),
        IS_EXCHANGING(2),
        DONE(3);

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
