package com.takeIt.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //    @NotEmpty(message = "Please provide a name")
    private String name;
    //    @NotEmpty(message = "Please provide a description")
    @Column(columnDefinition = "TEXT")
    private String description;
    private int gender;
    private int age_range;
    private int status;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Account account;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Category category;
    @OneToMany(mappedBy = "gift", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Set<Transaction> transaction;
    @OneToMany(mappedBy = "gift", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Set<ExchangeRequest> exchangeRequest;
    private String street_name;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private City city;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private District district;
    @Column(columnDefinition = "TEXT")
    private String thumbnail;
    private long createdAt;
    private long updatedAt;
    private long deletedAt;


    public enum Status {
        DELETED(-1),
        PENDING(0),
        ACTIVE(1),
        EXCHANGE_DONE(3),
        EXCHANGE_PENDING(2);

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

    public enum Gender {
        BOY(1),
        GIRL(2),
        OTHER(0);
        int value;

        Gender(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Gender findByValue(int value) {
            for (Gender gender : Gender.values()) {
                if (gender.getValue() == value) {
                    return gender;
                }
            }
            return null;
        }
    }

    public enum Age_range {
        NEWBORN(1), // 0 => 6 tháng
        INFANTS(2), // 6 => 12 tháng
        BABY(3), // 1 => 3 tuổi
        CHILD(4);  // > 4  tháng
        int value;

        Age_range(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Age_range findByValue(int value) {
            for (Age_range age_range : Age_range.values()) {
                if (age_range.getValue() == value) {
                    return age_range;
                }
            }
            return null;
        }

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getGender() {
        return gender;
    }

    public int getAge_range() {
        return age_range;
    }

    public int getStatus() {
        return status;
    }

    public Account getAccount() {
        return account;
    }

    public Category getCategory() {
        return category;
    }

    public Set<Transaction> getTransaction() {
        return transaction;
    }

    public String getStreet_name() {
        return street_name;
    }

    public City getCity() {
        return city;
    }

    public District getDistrict() {
        return district;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAge_range(int age_range) {
        this.age_range = age_range;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTransaction(Set<Transaction> transaction) {
        this.transaction = transaction;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<ExchangeRequest> getExchangeRequest() {
        return exchangeRequest;
    }

    public void setExchangeRequest(Set<ExchangeRequest> exchangeRequest) {
        this.exchangeRequest = exchangeRequest;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
