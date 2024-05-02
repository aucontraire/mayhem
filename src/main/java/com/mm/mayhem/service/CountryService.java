package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() { return countryRepository.findAll(); }

    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Optional<Country> getCountryByName(String name) { return countryRepository.findCountryByName(name); }

    public Country saveCountryWithCountryCode(Country country, String countryCode) {
        country.setCountryCode(countryCode);
        return countryRepository.save(country);
    }
}
