package com.takeIt.dto;

import com.takeIt.entity.City;
import com.takeIt.entity.District;
import com.takeIt.entity.Gift;
import com.takeIt.util.ObjectUtil;

import java.util.List;

public class DistrictDTO {
    private long id;
    private String name;
    private String cityName;

    public DistrictDTO(District district) {
        ObjectUtil.cloneObject(this, district);
        this.id = district.getId();
        this.name = district.getName();
        this.cityName = district.getCity().getName();
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
