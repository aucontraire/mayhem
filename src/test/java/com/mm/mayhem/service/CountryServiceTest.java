package com.mm.mayhem.service;

import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.repository.CountryRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CountryServiceTest {
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @Test
    public void testGetCountryById_Exists() {
        Country expectedCountry = new Country();
        expectedCountry.setId(1L);
        when(countryRepository.findById(1L)).thenReturn(Optional.of(expectedCountry));

        Optional<Country> actualOptionalCountry = countryService.getCountryById(1L);

        assertTrue(actualOptionalCountry.isPresent(), "Country should be present");
        Country actualCountry = actualOptionalCountry.get();
        assertEquals(expectedCountry, actualCountry, "Country should match");
        verify(countryRepository).findById(1L);
    }

    @Test
    public void testGetCountryById_NotExists() {
        // Given
        when(countryRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Country> actualOptionalCountry = countryService.getCountryById(1L);

        // Then
        assertFalse(actualOptionalCountry.isPresent(), "Country should not be present");
        verify(countryRepository).findById(1L); // Verify method call
    }
}
