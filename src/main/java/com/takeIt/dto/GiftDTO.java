package com.takeIt.dto;

import com.takeIt.entity.Gift;
import com.takeIt.util.DateTimeUtil;
import com.takeIt.util.ObjectUtil;

public class GiftDTO {
    private long id;
    private String name;
    private String description;
    private String gender;
    private String age_range;
    private String accountName;
    private String cityName;
    private String districtName;
    private String street_name;
    private String categoryName;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public GiftDTO(Gift gift) {
        ObjectUtil.cloneObject(this, gift);
        this.id = gift.getId();
        this.name = gift.getName();
        this.description = gift.getDescription();
        this.gender = gift.getGender() == 1 ? "Boy" : "Girl";
        this.cityName = gift.getCity().getName();
        this.districtName = gift.getDistrict().getName();
        this.street_name = gift.getStreet_name();
        this.accountName = gift.getAccount().getUsername();
        this.categoryName = gift.getCategory().getName();
        this.createdAt = DateTimeUtil.formatDateFromLong(gift.getCreatedAt());
        this.updatedAt = DateTimeUtil.formatDateFromLong(gift.getUpdatedAt());
        this.deletedAt = DateTimeUtil.formatDateFromLong(gift.getDeletedAt());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge_range() {
        return age_range;
    }

    public void setAge_range(String age_range) {
        this.age_range = age_range;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
