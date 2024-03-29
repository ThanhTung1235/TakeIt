package com.takeIt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Account {
    @Id
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private AccountInfo accountInfo;
    @OneToMany(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Gift> gifts;
    @OneToMany(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Credential> credential;
    private int status;
    private int role;
    private String salt;
    private long deletedAt;
    private long createdAt;
    private long updatedAt;

    public Account() {
        this.createdAt = Calendar.getInstance().getTimeInMillis();
        this.updatedAt = Calendar.getInstance().getTimeInMillis();
        this.status = Status.ACTIVE.getValue();
        this.role = Roles.MEMBER.getValue();
        this.id = Calendar.getInstance().getTimeInMillis();
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

    public Set<Credential> getCredential() {
        return credential;
    }

    public void setCredential(Set<Credential> credential) {
        this.credential = credential;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public enum Status {
        ACTIVE(1), UNACTIVE(0), DELETE(-1);

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

    public enum Roles {
        MEMBER(0),
        ADMIN(1);
        int value;

        Roles(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Roles findByValue(int value) {
            for (Roles roles : Roles.values()) {
                if (roles.getValue() == value) {
                    return roles;
                }
            }
            return null;
        }
    }

    public int getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role.getValue();
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    @JsonIgnore
    public Set<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
