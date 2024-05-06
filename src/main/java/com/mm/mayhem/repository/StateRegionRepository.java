package com.mm.mayhem.repository;

import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.model.db.geo.StateRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRegionRepository extends JpaRepository<StateRegion, Long> {
    Optional<List<StateRegion>> findStateRegionsByName(String name);
    Optional<StateRegion> findStateRegionByNameAndCountry(String name, Country country);
    Optional<StateRegion> findStateRegionByNameAndCountryName(String stateRegionName, String countryName);
    Optional<List<StateRegion>> findStateRegionsByCountry(Country country);
}
