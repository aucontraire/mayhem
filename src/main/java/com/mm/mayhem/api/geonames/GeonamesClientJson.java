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
        featureCodes.add("PPLA3");
        featureCodes.add("PPLA4");
        featureCodes.add("PPLX");
        featureCodes.add("PPLC");
    }

    public Geoname getCountry(Country country) {
        String countryName = country.getName();
        String countryCode = country.getCountryCode();

        String apiUrl = GEONAMES_BASE_URL + "{countryName}&name_equals={nameEquals}&country={countryCode}&username={username}";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("countryName", countryName);
        uriVariables.put("nameEquals", countryName);
        uriVariables.put("featureClass", "P");
        uriVariables.put("countryCode", countryCode);
        uriVariables.put("username", env.getProperty("mayhem.geonames.username"));

        GeonamesResponse response = restTemplate.getForObject(apiUrl, GeonamesResponse.class, uriVariables);

        List<Geoname> geonameMatches = new ArrayList<>();
        List<Geoname> geonameList = response.getGeonames();

        System.out.println("getCountry match count: " + geonameList.size());

        geonameList.forEach(geoname -> {
            geonameMatches.add(geoname);

        });
        return geonameMatches.get(0);
    }

    public String getCountryCode(Country country) {
        Geoname countryGeoname = getCountry(country);
        return countryGeoname.getCountryCode();
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

        System.out.println("getCity match count: " + geonameList.size());

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



    }

}
