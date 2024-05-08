package com.mm.mayhem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.mayhem.api.geonames.GeonamesClientJson;
import com.mm.mayhem.service.CityService;
import com.mm.mayhem.service.CountryService;
import com.mm.mayhem.service.StateRegionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() { return new ObjectMapper(); }

    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }

    @Bean
    public GeonamesClientJson geonamesClientJson(RestTemplate restTemplate, CityService cityService, Environment env) {
        return new GeonamesClientJson(restTemplate, cityService, env);
    }
}
