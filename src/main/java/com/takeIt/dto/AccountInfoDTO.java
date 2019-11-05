package com.takeIt.dto;

import com.takeIt.entity.AccountInfo;

import static java.lang.Long.valueOf;

public class AccountInfoDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long dob;
    private long createdAt;
    private long updatedAt;

    public AccountInfoDTO() {

    }

    public AccountInfoDTO(AccountInfo accountInfo){
        this.id = valueOf(accountInfo.getId());
        this.firstName = accountInfo.getFirstName();
        this.lastName = accountInfo.getLastName();
        this.email = accountInfo.getEmail();
        this.dob = valueOf(accountInfo.getDob());
        this.createdAt = valueOf(accountInfo.getCreatedAt());
        this.updatedAt = valueOf(accountInfo.getUpdatedAt());
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

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
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
}
