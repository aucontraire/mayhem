package com.mm.mayhem.controller;

import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.service.CountryService;
import com.mm.mayhem.service.StateRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public String getCountries(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());

        return "countries";
    }

    @GetMapping("/countries/{id}")
    public String getStateRegionsByCountry(@PathVariable Long id, Model model) {
        Optional<Country> countryOptional = countryService.getCountryById(id);
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            model.addAttribute("country", country);
        }
        return "stateregions";
    }

}
