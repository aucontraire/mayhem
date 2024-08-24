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

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public Double calculateDistanceBetweenCities(City city1, City city2) {
        // Calculate distance in miles using the Haversine formula
        Point location1 = city1.getLocation();
        Point location2 = city2.getLocation();

        double lat1 = Math.toRadians(location1.getY());
        double lon1 = Math.toRadians(location1.getX());
        double lat2 = Math.toRadians(location2.getY());
        double lon2 = Math.toRadians(location2.getX());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of the Earth in meters
        double radius = 6371000; // Approximate value for Earth's radius in meters

        return (radius * c) / 1609.34; // Convert to miles
    }


}
