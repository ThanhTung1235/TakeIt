package com.takeIt.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Credential {
    @Id
    private String accessToken;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Account account;
    private long tokenExpiredAt;
    private long createdAt;
    private long updatedAt;

    public Credential() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        this.createdAt = Calendar.getInstance().getTime().getTime();
        this.updatedAt = Calendar.getInstance().getTime().getTime();
        this.tokenExpiredAt = calendar.getTime().getTime();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getTokenExpiredAt() {
        return tokenExpiredAt;
    }

    public void setTokenExpiredAt(long tokenExpiredAt) {
        this.tokenExpiredAt = tokenExpiredAt;
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

    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().getTime().getTime());
    }
}
