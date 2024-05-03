package com.mm.mayhem.repository;

import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.StateRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<List<City>> findCitiesByName(String name);

    Optional<City> findCityByNameAndStateRegion(String name, StateRegion stateRegion);

    Optional<City> findCityByNameAndStateRegionName(String cityName, String stateRegionName);

    Optional<List<City>> findCitiesByLocationIsNull();
}
