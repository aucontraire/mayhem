package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.repository.StateRegionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mm.mayhem.utils.TestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class StateRegionServiceTest {
    @Mock
    private StateRegionRepository stateRegionRepository;

    @InjectMocks
    private StateRegionService stateRegionService;

    @Test
    public void testGetStateRegionById_Exists() {
        StateRegion expectedStateRegion = TestHelper.createStateRegion(1L, "Oaxaca");
        when(stateRegionRepository.findById(1L)).thenReturn(Optional.of(expectedStateRegion));

        Optional<StateRegion> actualOptionalStateRegion = stateRegionService.getStateRegionById(1L);

        assertTrue(actualOptionalStateRegion.isPresent(), "StateRegion should be present");
        StateRegion actualStateRegion = actualOptionalStateRegion.get();
        assertEquals(expectedStateRegion, actualStateRegion, "StateRegion should match");
        verify(stateRegionRepository).findById(1L);
    }

    @Test
    public void testGetStateRegionById_NotExists() {
        // Given
        when(stateRegionRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<StateRegion> actualOptionalStateRegion = stateRegionService.getStateRegionById(1L);

        // Then
        assertFalse(actualOptionalStateRegion.isPresent(), "StateRegion should not be present");
        verify(stateRegionRepository).findById(1L); // Verify method call
    }

    @Test
    public void testGetStateRegionsByName_Exists() {
        List<StateRegion> expectedStateRegionList = TestHelper.createStateRegionList(2, "Oaxaca");
        when(stateRegionRepository.findStateRegionsByName("Oaxaca")).thenReturn(Optional.of(expectedStateRegionList));

        Optional<List<StateRegion>> actualOptionalStateRegionList = stateRegionService.getStateRegionsByName("Oaxaca");

        assertTrue(actualOptionalStateRegionList.isPresent(), "StateRegion list should be present");
        List<StateRegion> actualStateRegionList = actualOptionalStateRegionList.get();
        assertEquals(expectedStateRegionList, actualStateRegionList, "StateRegion list should match");
        verify(stateRegionRepository).findStateRegionsByName("Oaxaca");
    }

    @Test
    public void testGetStateRegionsByName_NotExists() {
        when(stateRegionRepository.findStateRegionsByName("Oaxaca")).thenReturn(Optional.empty());

        Optional<List<StateRegion>> actualOptionalStateRegion = stateRegionService.getStateRegionsByName("Oaxaca");

        assertFalse(actualOptionalStateRegion.isPresent(), "StateRegion list should not be present");
        verify(stateRegionRepository).findStateRegionsByName("Oaxaca");
    }
}
