package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.repository.StateRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateRegionService {
    private final StateRegionRepository stateRegionRepository;

    @Autowired
    public StateRegionService(StateRegionRepository stateRegionRepository) {
        this.stateRegionRepository = stateRegionRepository;
    }

    public Optional<StateRegion> getStateRegionById(Long id) {
        return stateRegionRepository.findById(id);
    }
}
