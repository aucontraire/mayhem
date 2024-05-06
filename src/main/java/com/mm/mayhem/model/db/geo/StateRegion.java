package com.mm.mayhem.model.db.geo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mm.mayhem.model.db.BaseModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "state_region")
public class StateRegion extends BaseModel {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "stateRegion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<City> cities = new ArrayList<>();

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

    @JsonIgnore
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<City> getCities() { return cities; }

    public void setCities(List<City> cities) { this.cities = cities; }
}
