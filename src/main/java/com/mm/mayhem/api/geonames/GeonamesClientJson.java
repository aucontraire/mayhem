package com.mm.mayhem.api.geonames;

import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.service.CityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GeonamesClientJson {
    private static final String GEONAMES_BASE_URL = "http://api.geonames.org/searchJSON?q=";
    private final Environment env;
    private final CityService cityService;
    private final RestTemplate restTemplate;
    private final List<String> featureCodes = Arrays.asList("PPL", "PPLA", "PPLA2", "PPLA3", "PPLA4", "PPLX", "PPLC");
    private static final Logger logger = LoggerFactory.getLogger(GeonamesClientJson.class);

    public GeonamesClientJson(RestTemplate restTemplate, CityService cityService, Environment env) {
        this.restTemplate = restTemplate;
        this.cityService = cityService;
        this.env = env;
    }

    public String buildApiUrl(String query, String featureClass, String countryCode) {
        return String.format("%s%s&name_equals=%s&featureClass=%s&country=%s&username=%s",
                GEONAMES_BASE_URL, query, query, featureClass, countryCode, env.getProperty("mayhem.geonames.username"));
    }

    private List<Geoname> filterGeonames(List<Geoname> geonameList, String stateRegionName) {
        List<Geoname> filteredGeonames = new ArrayList<>();
        for (Geoname geoname : geonameList) {
            String adminName1 = geoname.getAdminName1();
            String fCode = geoname.getFcode();
            if (adminName1.contains(stateRegionName) && featureCodes.contains(fCode)) {
                filteredGeonames.add(geoname);
            }
        }
        logger.info("Geonames match count: " + filteredGeonames.size());
        return filteredGeonames;
    }

    public Geoname getCountry(Country country) {
        String apiUrl = buildApiUrl(country.getName(), "A", country.getCountryCode());
        GeonamesResponse response = restTemplate.getForObject(apiUrl, GeonamesResponse.class);
        List<Geoname> geonameList = response.getGeonames();
        logger.info("Country match count: " + geonameList.size());
        return geonameList.isEmpty() ? null : geonameList.get(0);
    }

    public String getCountryCode(Country country) {
        Geoname countryGeoname = getCountry(country);
        return countryGeoname.getCountryCode();
    }

    public List<Geoname> getCity(City city) {
        String apiUrl = buildApiUrl(city.getStandardizedName(), "P", city.getStateRegion().getCountry().getCountryCode());
        GeonamesResponse response = restTemplate.getForObject(apiUrl, GeonamesResponse.class);
        List<Geoname> geonameList = response.getGeonames();
        return filterGeonames(geonameList, city.getStateRegion().getName());
    }

    public void addLocationToCity(City city) {
        List<Geoname> geonameCandidateCityList = getCity(city);
        logger.info("City location match count: " + geonameCandidateCityList.size());

        int highestPopulation = 0;
        Geoname cityHighestPopulation = null;
        for (Geoname geoname : geonameCandidateCityList) {
            int population = geoname.getPopulation();
            String adminName1 = geoname.getAdminName1();
            String fCode = geoname.getFcode();
            int geonameId = geoname.getGeonameId();

            logger.info(geonameId + " " + adminName1 + " " + fCode +  " population: " + population);
            if (population >= highestPopulation) {
                highestPopulation = population;
                cityHighestPopulation = geoname;
            }
        }

        if (cityHighestPopulation != null) {
            double latitude = cityHighestPopulation.getLat();
            double longitude = cityHighestPopulation.getLng();
            cityService.saveCityWithLocation(city, latitude, longitude);
            logger.info("Found a match: " + city.getName() + " Population: " + highestPopulation + " Latitude: " + latitude + " Longitude: " + longitude);
        } else {
            logger.info("No matching geonames found for city: " + city.getName());
        }
    }
}
