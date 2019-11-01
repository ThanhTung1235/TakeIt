package com.takeIt.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private long createdAt;
    private long updatedAt;
    @OneToOne(mappedBy = "account",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private AccountInfo accountInfo;
    @OneToMany(mappedBy = "account" ,cascade = CascadeType.ALL)
    private Set<Gift> gifts;
    private int status;
    private int role;

    public Account() {
        this.createdAt = Calendar.getInstance().getTimeInMillis();
        this.updatedAt = Calendar.getInstance().getTimeInMillis();
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
}
