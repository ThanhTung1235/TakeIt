package com.takeIt.dto;

import com.takeIt.entity.Category;
import com.takeIt.util.ObjectUtil;

public class CategoryDTO {
    private long id;
    private String name;

    public CategoryDTO(Category category) {
        ObjectUtil.cloneObject(this, category);
        this.id = category.getId();
        this.name = category.getName();
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
