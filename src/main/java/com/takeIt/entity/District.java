package com.takeIt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "devvn_quanhuyen")
public class District {
    @Id
    @Column(name = "maqh")
    private long id;
    private String name;
    private String type;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "matp")
    private City city;
    @OneToMany(mappedBy = "district", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Gift> gifts;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @JsonIgnore
    public Set<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }
}
