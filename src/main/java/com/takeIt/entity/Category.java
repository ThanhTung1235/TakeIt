package com.takeIt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {
    @Id
    private long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    Set<Gift> gifts;

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

    @JsonIgnore
    public Set<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }
}

