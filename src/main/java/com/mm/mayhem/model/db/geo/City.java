package com.mm.mayhem.model.db.geo;

import com.mm.mayhem.model.db.BaseModel;
import jakarta.persistence.*;

@Entity
@Table(name = "city")
public class City extends BaseModel {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_region_id")
    private StateRegion stateRegion;

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

    public StateRegion getStateRegion() {
        return stateRegion;
    }

    public void setStateRegion(StateRegion stateRegion) {
        this.stateRegion = stateRegion;
    }
}
