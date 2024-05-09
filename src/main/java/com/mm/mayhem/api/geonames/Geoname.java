package com.mm.mayhem.api.geonames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geoname {

    private String adminCode1;
    private double lng;
    private int geonameId;
    private String toponymName;
    private String countryId;
    private String fcl;
    private int population;
    private String countryCode;
    private String name;
    private String fclName;
    private Map<String, String> adminCodes1;
    private String countryName;
    private String fcodeName;
    private String adminName1;
    private double lat;
    private String fcode;

    // Add getters and setters for other fields as needed

    public String getAdminCode1() {
        return adminCode1;
    }

    public void setAdminCode1(String adminCode1) {
        this.adminCode1 = adminCode1;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(int geonameId) {
        this.geonameId = geonameId;
    }

    public String getToponymName() {
        return toponymName;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getFcl() {
        return fcl;
    }

    public void setFcl(String fcl) {
        this.fcl = fcl;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFclName() {
        return fclName;
    }

    public void setFclName(String fclName) {
        this.fclName = fclName;
    }

    public Map<String, String> getAdminCodes1() {
        return adminCodes1;
    }

    public void setAdminCodes1(Map<String, String> adminCodes1) {
        this.adminCodes1 = adminCodes1;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFcodeName() {
        return fcodeName;
    }

    public void setFcodeName(String fcodeName) {
        this.fcodeName = fcodeName;
    }

    public String getAdminName1() {
        return adminName1;
    }

    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }
}

