package com.takeIt.dto;

import com.takeIt.entity.City;
import com.takeIt.util.ObjectUtil;



public class CityDTO {
    private long id;
    private String name;

    public CityDTO(City city) {
        ObjectUtil.cloneObject(this, city);
        this.id = city.getId();
        this.name = city.getName();
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
}
