package com.mm.mayhem.model.db.geo;

import com.mm.mayhem.model.db.BaseModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "country")
public class Country extends BaseModel {
    @Id
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "country_code", unique = true)
    private String countryCode;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StateRegion> stateRegions = new ArrayList<>();

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<StateRegion> getStateRegions() {
        return stateRegions;
    }

    public void setStateRegions(List<StateRegion> stateRegions) {
        this.stateRegions = stateRegions;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
