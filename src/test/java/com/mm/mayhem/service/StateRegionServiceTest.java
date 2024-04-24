package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.repository.StateRegionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class StateRegionServiceTest {
    @Mock
    private StateRegionRepository stateRegionRepository;

    @InjectMocks
    private StateRegionService stateRegionService;

    @Test
    public void testGetStateRegionById_Exists() {
        StateRegion expectedStateRegion = new StateRegion();
        expectedStateRegion.setId(1L);
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
}
