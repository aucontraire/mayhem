package com.mm.mayhem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceDto implements LocationDto {
    @JsonProperty("id")
    private Long cityId;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("state_region__id")
    private Long stateRegionId;

    @JsonProperty("state_region__name")
    private String stateRegionName;

    @JsonProperty("state_region__country__id")
    private Long countryId;

    @JsonProperty("state_region__country__name")
    private String countryName;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getStateRegionId() {
        return stateRegionId;
    }

    public void setStateRegionId(Long stateRegionId) {
        this.stateRegionId = stateRegionId;
    }

    public String getStateRegionName() {
        return stateRegionName;
    }

    public void setStateRegionName(String stateRegionName) {
        this.stateRegionName = stateRegionName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
