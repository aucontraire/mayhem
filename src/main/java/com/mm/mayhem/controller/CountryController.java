package com.mm.mayhem.controller;

import com.mm.mayhem.api.geonames.GeonamesClientJson;
import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.Coordinates;
import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CountryController {
    private final CountryService countryService;
    private final Environment env;

    private final GeonamesClientJson geonamesClientJson;

    @Autowired
    public CountryController(CountryService countryService, Environment env, GeonamesClientJson geonamesClientJson) {
        this.countryService = countryService;
        this.env = env;
        this.geonamesClientJson = geonamesClientJson;
    }

    @GetMapping("/countries")
    public String getCountries(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());

        return "countries";
    }

    @GetMapping("/countries/{id}")
    public String getStateRegionsByCountry(@PathVariable Long id, Model model) {
        String mapboxAccessToken = env.getProperty("mayhem.mapbox.api.token");
        model.addAttribute("mapboxAccessToken", mapboxAccessToken);

        Optional<Country> countryOptional = countryService.getCountryById(id);
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            Optional<Coordinates> coordinatesOptional = geonamesClientJson.getCountryCoordinates(country);
            if (coordinatesOptional.isPresent()) {
                Coordinates coordinates = coordinatesOptional.get();
                model.addAttribute("longitude", coordinates.getLongitude());
                model.addAttribute("latitude", coordinates.getLatitude());
            }

            List<City> cities = country.getStateRegions().stream().flatMap(sr -> sr.getCities().stream()).collect(Collectors.toList());
            model.addAttribute("cities", cities);
            model.addAttribute("country", country);
        }
        return "stateregions";
    }
}
