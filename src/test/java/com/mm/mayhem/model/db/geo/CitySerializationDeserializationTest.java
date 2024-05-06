package com.mm.mayhem.model.db.geo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.mayhem.model.db.geo.City;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CitySerializationDeserializationTest {

    @Test
    public void testCitySerializationDeserialization() throws Exception {
        // Create a City object
        City city = new City();
        city.setId(1L);
        city.setName("New York");

        // Create a Point object representing the location
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(-74.0059, 40.7128));
        city.setLocation(point);

        // Serialize the City object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String cityJson = objectMapper.writeValueAsString(city);

        // Deserialize the JSON back to a City object
        City deserializedCity = objectMapper.readValue(cityJson, City.class);

        // Assertions
        assertEquals(city.getId(), deserializedCity.getId());
        assertEquals(city.getName(), deserializedCity.getName());
        assertEquals(city.getLocation().getX(), deserializedCity.getLocation().getX(), 0.0001);
        assertEquals(city.getLocation().getY(), deserializedCity.getLocation().getY(), 0.0001);
    }
}
