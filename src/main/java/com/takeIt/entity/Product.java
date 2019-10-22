package com.takeIt.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //    @NotEmpty(message = "Please provide a name")
    private String name;
    //    @NotEmpty(message = "Please provide a description")
    private String description;
    private int gender;
    private int age_range;
    private int status;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Account account;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Category category;
    @OneToOne
    private Address address;
    @OneToOne(mappedBy = "product")
    private Transaction transaction;
    private long createdAt;
    private long updatedAt;
    private long deletedAt;

    public Product() {
        this.createdAt = Calendar.getInstance().getTimeInMillis();
        this.updatedAt = Calendar.getInstance().getTimeInMillis();
        this.status = Status.PENDING.getValue();
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

    public Address getAddress() {
        return address;
    }

    public Transaction getTransaction() {
        return transaction;
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

    public enum Status {
        DELETED(-1),
        PENDING(0),
        ACTIVE(1);
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
        BOY(1), GIRL(2), OTHER(0);
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

    public static final class ProductBuilder {
        private long id;
        //    @NotEmpty(message = "Please provide a name")
        private String name;
        //    @NotEmpty(message = "Please provide a description")
        private String description;
        private int gender;
        private int age_range;
        private int status;
        private Account account;
        private Category category;
        private Address address;
        private Transaction transaction;
        private long createdAt;
        private long updatedAt;
        private long deletedAt;

        public ProductBuilder() {
        }

        public static ProductBuilder aProduct() {
            return new ProductBuilder();
        }

        public ProductBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder setGender(Gender gender) {
            this.gender = gender.getValue();
            return this;
        }

        public ProductBuilder setAge_range(int age_range) {
            this.age_range = age_range;
            return this;
        }

        public ProductBuilder setStatus(Status status) {
            this.status = status.getValue();
            return this;
        }

        public ProductBuilder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public ProductBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public ProductBuilder setTransaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public ProductBuilder setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductBuilder setUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductBuilder setDeletedAt(long deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.age_range = this.age_range;
            product.address = this.address;
            product.account = this.account;
            product.gender = this.gender;
            product.deletedAt = this.deletedAt;
            product.updatedAt = this.updatedAt;
            product.description = this.description;
            product.createdAt = this.createdAt;
            product.transaction = this.transaction;
            product.status = this.status;
            product.category = this.category;
            product.id = this.id;
            product.name = this.name;
            return product;
        }
    }
}
