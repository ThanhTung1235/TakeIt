package com.takeIt.dto;

import com.takeIt.entity.Account;
import com.takeIt.util.DateTimeUtil;
import com.takeIt.util.ObjectUtil;

public class AccountDTO {
    private long id;
    private String username;
    private String password;
    private String createdAt;
    private String updatedAt;
    private String status;
    private String role;

    public AccountDTO(Account account) {
        ObjectUtil.cloneObject(this, account);
        this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.status = account.getStatus() == 1 ? "Active" : "UnActive";
        this.role = account.getRole() == 1 ? "Admin" : "Member";
        this.createdAt = DateTimeUtil.formatDateFromLong(account.getCreatedAt());
        this.updatedAt = DateTimeUtil.formatDateFromLong(account.getUpdatedAt());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
