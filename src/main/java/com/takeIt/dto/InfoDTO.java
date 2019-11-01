package com.takeIt.dto;

import com.takeIt.entity.AccountInfo;
import com.takeIt.util.DateTimeUtil;
import org.springframework.util.ObjectUtils;

public class InfoDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private String accountName;
    private long accountId;
    private String createdAt;
    private String updatedAt;

    public InfoDTO(AccountInfo info) {

        this.id = info.getId();
        this.firstName = info.getFirstName();
        this.lastName = info.getLastName();
        this.accountId = info.getAccount().getId();
        this.accountName = info.getAccount().getUsername();
        this.createdAt = DateTimeUtil.formatDateFromLong(info.getCreatedAt());
        this.updatedAt = DateTimeUtil.formatDateFromLong(info.getUpdatedAt());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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
}
