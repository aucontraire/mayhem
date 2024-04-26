package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public Optional<List<City>> getCitiesByName(String name) {
        return cityRepository.findCitiesByName(name);
    }

    public Optional<City> getCityByNameAndStateRegion(String name, StateRegion stateRegion) {
        return cityRepository.findCityByNameAndStateRegion(name, stateRegion);
    }

    public Optional<City> getCityByNameAndStateRegionName(String cityName, String stateRegionName) {
        return cityRepository.findCityByNameAndStateRegionName(cityName, stateRegionName);
    }
}
