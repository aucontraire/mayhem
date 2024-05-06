package com.mm.mayhem.api.geonames;

import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.service.CityService;
import com.mm.mayhem.service.CountryService;
import com.mm.mayhem.service.StateRegionService;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeonamesClientJson {
    private static final String GEONAMES_BASE_URL = "http://api.geonames.org/searchJSON?q=";
    private final Environment env;
    private final CityService cityService;
    private final StateRegionService stateRegionService;
    private final CountryService countryService;
    private final RestTemplate restTemplate;
    private List<String> featureCodes = new ArrayList<>();

    public GeonamesClientJson(RestTemplate restTemplate, CityService cityService, StateRegionService stateRegionService, CountryService countryService, Environment env) {
        this.restTemplate = restTemplate;
        this.cityService = cityService;
        this.stateRegionService = stateRegionService;
        this.countryService = countryService;
        this.env = env;
        featureCodes.add("PPL");
        featureCodes.add("PPLA");
        featureCodes.add("PPLA2");
        featureCodes.add("PPLX");
        featureCodes.add("PPLC");
    }

    public List<Geoname> getCity(City city) {

        String cityName = city.getStandardizedName();

        StateRegion stateRegion = city.getStateRegion();
        String stateRegionName = stateRegion.getName();
        Country country = stateRegion.getCountry();
        String countryName = country.getName();
        String countryCode = country.getCountryCode();

        String apiUrl = GEONAMES_BASE_URL + "{city}&name_equals={nameEquals}&featureClass={featureClass}&country={country}&username={username}";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("city", cityName);
        uriVariables.put("nameEquals", cityName);
        uriVariables.put("featureClass", "P");
        uriVariables.put("country", countryCode);
        uriVariables.put("username", env.getProperty("mayhem.geonames.username"));

        GeonamesResponse response = restTemplate.getForObject(apiUrl, GeonamesResponse.class, uriVariables);

        List<Geoname> geonameMatches = new ArrayList<>();
        List<Geoname> geonameList = response.getGeonames();

        System.out.println("getCity Match Count: " + geonameList.size());

        geonameList.forEach(geoname -> {
            String adminName1 = geoname.getAdminName1();
            String fCode = geoname.getFcode();
            if (adminName1.contains(stateRegionName) && featureCodes.contains(fCode)) {
                geonameMatches.add(geoname);
            }
        });
        return geonameMatches;
    }

    public void addLocationToCity(City city) {
        List<Geoname> geonameCandidateCityList = getCity(city);
        System.out.println("addLocationToCity Match Count: " + geonameCandidateCityList.size());

        int highestPopulation = 0;
        Geoname cityHighestPopulation = null;
        for (Geoname geoname : geonameCandidateCityList) {
            int population = geoname.getPopulation();
            String adminName1 = geoname.getAdminName1();
            String fCode = geoname.getFcode();
            int geonameId = geoname.getGeonameId();

            System.out.println(geonameId + " " + adminName1 + " " + fCode +  " population: " + population);
            if (population >= highestPopulation) {
                highestPopulation = population;
                cityHighestPopulation = geoname;
            }
        }

        if (cityHighestPopulation != null) {
            double latitude = cityHighestPopulation.getLat();
            double longitude = cityHighestPopulation.getLng();
            cityService.saveCityWithLocation(city, latitude, longitude);
            System.out.println("Found a match: " + city.getName() + " Population: " + highestPopulation + " Latitude: " + latitude + " Longitude: " + longitude);
        } else {
            System.out.println("No matching geonames found for city: " + city.getName());
        }

        /*
        List<Geoname> geonameCandidateCityList = getCity(city);
        int highestPopulation = 0;
        Geoname cityHighestPopulation = null;
        geonameCandidateCityList.forEach(geoname -> {
            String cityName = city.getName();
            int geonameId = geoname.getGeonameId();
            double latitude = geoname.getLat();
            double longitude = geoname.getLng();
            String adminName1 = geoname.getAdminName1();
            String fcl = geoname.getFcl();
            String fCode = geoname.getFcode();
            String fcodeName = geoname.getFcodeName();
            int population =  geoname.getPopulation();
            String adminCode1 = geoname.getAdminCode1();

            if (geonameCandidateCityList.size() == 1) {
                cityService.saveCityWithLocation(city, latitude, longitude);
                System.out.println("Found a match: " + cityName + " " + geonameId + " " + adminName1 + " " + latitude + " " + longitude + " " + population + " featureClass:" + fcl + " fCode:" + fCode + " fcodeName:" + fcodeName);
            } else {
                if (population > highestPopulation) {
                    cityHighestPopulation = geoname;
                }
                System.out.println("Other matches: " + cityName + " " + geonameId + " " + adminName1 + " " + latitude + " " + longitude + " " + population + " featureClass:" + fcl + " fCode:" + fCode + " fcodeName:" + fcodeName);
            }
        });
        cityService.saveCityWithLocation(city, cityHighestPopulation.getLat(), cityHighestPopulation.getLng());
        */

    }

}