package com.mm.mayhem.api.geonames;

import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.Coordinates;
import com.mm.mayhem.model.db.geo.Country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GeonamesClientJson {
    private static final String GEONAMES_BASE_URL = "http://api.geonames.org/searchJSON?q=";
    private final Environment env;
    private final RestTemplate restTemplate;
    private final List<String> featureCodes = Arrays.asList("PPL", "PPLA", "PPLA2", "PPLA3", "PPLA4", "PPLX", "PPLC");
    private static final Logger logger = LoggerFactory.getLogger(GeonamesClientJson.class);

    @Autowired
    public GeonamesClientJson(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
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

    public GeonamesResponse getCountry(Country country) {
        try {
            String apiUrl = buildApiUrl(country.getName(), "A", country.getCountryCode());
            ResponseEntity<GeonamesResponse> responseEntity = restTemplate.exchange(
                    apiUrl, HttpMethod.GET, null, GeonamesResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                logger.error("Error fetching " + country.getName() + " country data: " + responseEntity.getStatusCode());
                return null;
            }
        } catch (HttpClientErrorException ex) {
            logger.error("HTTP fetching " + country.getName() + " country data: " + ex.getMessage());
            return null;
        } catch (RestClientException ex) {
            logger.error("RestClient fetching " + country.getName() + " country data: " + ex.getMessage());
            return null;
        }
    }

    public Optional<String> getCountryCode(Country country) {
        GeonamesResponse geonamesResponse = getCountry(country);
        List<Geoname> countryGeonameList = geonamesResponse.getGeonames();
        if (countryGeonameList.isEmpty()) {
            return Optional.empty();
        }
        Geoname countryGeoname = countryGeonameList.get(0);
        return Optional.of(countryGeoname.getCountryCode());
    }

    public Coordinates getGeonamesCoordinates(Geoname geoname) {
        Coordinates coordinates = new Coordinates();
        Double longitude = geoname.getLng();
        Double latitude = geoname.getLat();
        coordinates.setLongitude(longitude);
        coordinates.setLatitude(latitude);
        return coordinates;
    }

    public Optional<Coordinates> getCountryCoordinates(Country country) {
        GeonamesResponse geonamesResponse = getCountry(country);
        List<Geoname> countryGeonameList = geonamesResponse.getGeonames();
        if (!countryGeonameList.isEmpty()) {
            Geoname countryGeoname = countryGeonameList.get(0);
            return Optional.of(getGeonamesCoordinates(countryGeoname));
        }
        return Optional.empty();
    }

    public GeonamesResponse getCity(City city) {
        try {
            String apiUrl = buildApiUrl(city.getStandardizedName(), "P", city.getStateRegion().getCountry().getCountryCode());
            ResponseEntity<GeonamesResponse> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, GeonamesResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                logger.error("Error fetching " + city.getName() + " city data: " + responseEntity.getStatusCode());
                return null;
            }
        } catch (HttpClientErrorException ex) {
            logger.error("HTTP Error fetching " + city.getName() + " city data: " + ex.getMessage());
            return null;
        } catch (RestClientException ex) {
            logger.error("RestClient Error fetching " + city.getName() + " city data: " + ex.getMessage());
            return null;
        }
    }

    public Optional<Coordinates> getCityCoordinates(City city) {
        GeonamesResponse geonamesResponse = getCity(city);
        List<Geoname> cityGeonameList = filterGeonames(geonamesResponse.getGeonames(), city.getStateRegion().getName());
        if (cityGeonameList.isEmpty()) {
            return Optional.empty();
        }

        int highestPopulation = 0;
        Geoname cityHighestPopulation = null;
        for (Geoname geoname : cityGeonameList) {
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
            return Optional.of(getGeonamesCoordinates(cityHighestPopulation));
        }
        return Optional.empty();
    }
}
