package com.mm.mayhem.api.geonames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeonamesResponse {

    private int totalResultsCount;
    private List<Geoname> geonames;

    public int getTotalResultsCount() {
        return totalResultsCount;
    }

    public void setTotalResultsCount(int totalResultsCount) {
        this.totalResultsCount = totalResultsCount;
    }

    public List<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<Geoname> geonames) {
        this.geonames = geonames;
    }

    @Override
    public String toString() {
        return "GeonamesResponse{" +
                "totalResultsCount=" + totalResultsCount +
                ", geonames=" + geonames +
                '}';
    }
}
