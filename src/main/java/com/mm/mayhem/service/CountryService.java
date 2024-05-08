package com.mm.mayhem.service;

import com.mm.mayhem.api.geonames.GeonamesClientJson;
import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final GeonamesClientJson geonamesClientJson;

    @Autowired
    public CountryService(CountryRepository countryRepository, GeonamesClientJson geonamesClientJson) {
        this.countryRepository = countryRepository;
        this.geonamesClientJson = geonamesClientJson;
    }

    public List<Country> getAllCountries() { return countryRepository.findAll(); }

    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Optional<Country> getCountryByName(String name) { return countryRepository.findCountryByName(name); }

    public Country saveCountry(Country country) {
        if (country.getCountryCode() == null) {
            String countryCode = geonamesClientJson.getCountryCode(country);
            country.setCountryCode(countryCode);
        }
        return countryRepository.save(country);
    }
}
