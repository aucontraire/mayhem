package com.mm.mayhem.controller;

import com.mm.mayhem.api.geonames.GeonamesClientJson;
import com.mm.mayhem.model.db.geo.Coordinates;
import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.model.db.geo.StateRegion;
import com.mm.mayhem.service.StateRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class StateRegionController {
    private final StateRegionService stateRegionService;
    private final GeonamesClientJson geonamesClientJson;
    private final Environment env;

    @Autowired
    public StateRegionController(StateRegionService stateRegionService, GeonamesClientJson geonamesClientJson, Environment env) {
        this.stateRegionService = stateRegionService;
        this.geonamesClientJson = geonamesClientJson;
        this.env = env;
    }

    @GetMapping("/stateregions/{id}")
    public String getCitiesByStateRegion(@PathVariable Long id, Model model) {
        String mapboxAccessToken = env.getProperty("mayhem.mapbox.api.token");
        model.addAttribute("mapboxAccessToken", mapboxAccessToken);
        Optional<StateRegion> stateRegionOptional = stateRegionService.getStateRegionById(id);
        if (stateRegionOptional.isPresent()) {
            StateRegion stateRegion = stateRegionOptional.get();

            Country country = stateRegion.getCountry();
            Optional<Coordinates> coordinatesOptional = geonamesClientJson.getCountryCoordinates(country);
            if (coordinatesOptional.isPresent()) {
                Coordinates coordinates = coordinatesOptional.get();
                model.addAttribute("longitude", coordinates.getLongitude());
                model.addAttribute("latitude", coordinates.getLatitude());
            }

            model.addAttribute("stateRegion", stateRegion);
        }
        return "cities";
    }
}
