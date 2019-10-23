package com.takeIt.entity;

import javax.persistence.*;

@Entity
@Table(name = "devvn_tinhthanhpho")
public class City {
    @Id
    @Column(name = "matp")
    private long id;
    private String name;
    private String type;
    @OneToOne(mappedBy = "city")
    private Gift gift;

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

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }
}
