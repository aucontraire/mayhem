package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.repository.CityRepository;

import com.mm.mayhem.utils.GeographyUtil;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import org.locationtech.jts.geom.PrecisionModel;
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

    public Double calculateDistanceBetweenCities(City city1, City city2) {
        Point point1 = city1.getLocation();
        Point point2 = city2.getLocation();
        /*
        Point location1 = city1.getLocation();
        Point location2 = city2.getLocation();

        double latitude1 = location1.getY();
        double longitude1 = location1.getX();

        double latitude2 = location2.getY();
        double longitude2 = location2.getX();

        // Create Coordinate objects for the two points
        Coordinate coord1 = new Coordinate(longitude1, latitude1);
        Coordinate coord2 = new Coordinate(longitude2, latitude2);

        // Create Point objects using the Coordinate objects
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326); // 4326 is the SRID for WGS84
        Point point1 = geometryFactory.createPoint(coord1);
        Point point2 = geometryFactory.createPoint(coord2);
        */

        // Calculate the distance between the two points
        return point1.distance(point2);
    }


}
