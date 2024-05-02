package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.repository.StateRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateRegionService {
    private final StateRegionRepository stateRegionRepository;

    @Autowired
    public StateRegionService(StateRegionRepository stateRegionRepository) {
        this.stateRegionRepository = stateRegionRepository;
    }

    public List<StateRegion> getAllStateRegions() { return stateRegionRepository.findAll(); }

    public Optional<StateRegion> getStateRegionById(Long id) {
        return stateRegionRepository.findById(id);
    }

    public Optional<List<StateRegion>> getStateRegionsByName(String name) {
        return stateRegionRepository.findStateRegionsByName(name);
    }

    public Optional<StateRegion> getStateRegionByNameAndCountry(String name, Country country) {
        return stateRegionRepository.findStateRegionByNameAndCountry(name, country);
    }

    public Optional<StateRegion> getStateRegionByNameAndCountryName(String stateRegionName, String countryName) {
        return stateRegionRepository.findStateRegionByNameAndCountryName(stateRegionName, countryName);
    }
}
