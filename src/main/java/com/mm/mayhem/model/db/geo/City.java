package com.mm.mayhem.model.db.geo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mm.mayhem.model.db.BaseModel;
import jakarta.persistence.*;
import org.apache.commons.text.WordUtils;
import org.locationtech.jts.geom.Point;


@Entity
@Table(name = "city")
public class City extends BaseModel {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonSerialize(using = PointSerializer.class)
    @JsonDeserialize(using = PointDeserializer.class)
    @Column(name = "location")
    private Point location;

    @JsonIgnore
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

    @JsonIgnore
    public String getStandardizedName() {
        if (name.equals(name.toUpperCase())) {
            return WordUtils.capitalizeFully(name);
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() { return location; }

    public void setLocation(Point location) {
        this.location = location;
    }

    public StateRegion getStateRegion() {
        return stateRegion;
    }

    public void setStateRegion(StateRegion stateRegion) {
        this.stateRegion = stateRegion;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stateRegion=" + (stateRegion != null ? stateRegion.getName() : "null") +
                ", country=" + (stateRegion != null && stateRegion.getCountry() != null ? stateRegion.getCountry().getName() : "null") +
                '}';
    }

}
