package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.repository.CityRepository;

import com.mm.mayhem.utils.GeographyUtil;
import org.locationtech.jts.geom.Point;

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

    public List<City> getAllCities() { return cityRepository.findAll(); }

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

    public Optional<List<City>> getCitiesByLocationIsNotNull() {
        return cityRepository.findCitiesByLocationIsNotNull();
    }

    public Optional<List<City>> getCitiesByLocationIsNull() {
        return cityRepository.findCitiesByLocationIsNull();
    }

    public City saveCityWithLocation(City city, Double latitude, Double longitude) {
        Point point = GeographyUtil.createPoint(latitude, longitude);
        city.setLocation(point);
        return cityRepository.save(city);
    }

}
