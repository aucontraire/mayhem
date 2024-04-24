package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }
}
