package com.mm.mayhem.repository;

import com.mm.mayhem.model.db.geo.StateRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRegionRepository extends JpaRepository<StateRegion, Long> {
}
