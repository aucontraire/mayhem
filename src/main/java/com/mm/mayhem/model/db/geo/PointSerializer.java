package com.mm.mayhem.model.db.geo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;

import java.io.IOException;

public class PointSerializer extends JsonSerializer<Point> {

    @Override
    public void serialize(Point value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Coordinate coordinate = value.getCoordinate();
        gen.writeStartObject();
        gen.writeNumberField("x", coordinate.getX());
        gen.writeNumberField("y", coordinate.getY());
        gen.writeEndObject();
    }
}
