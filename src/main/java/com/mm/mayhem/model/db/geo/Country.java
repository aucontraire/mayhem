package com.mm.mayhem.model.db.geo;

import com.mm.mayhem.model.db.BaseModel;
import jakarta.persistence.*;

@Entity
@Table(name = "country")
public class Country extends BaseModel {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
