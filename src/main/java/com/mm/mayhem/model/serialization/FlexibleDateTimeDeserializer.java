package com.mm.mayhem.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FlexibleDateTimeDeserializer extends JsonDeserializer<Object> {

    private final List<DateTimeFormatter> localDateTimeFormatters = new ArrayList<>();
    private final List<DateTimeFormatter> zonedDateTimeFormatters = new ArrayList<>();

    public FlexibleDateTimeDeserializer() {
        // Adding patterns to handle various LocalDateTime formats
        localDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        localDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS"));
        localDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS"));
        localDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        localDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Adding patterns to handle various ZonedDateTime formats (with timezone)
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSXXX"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

        // Adding 'Z' suffix patterns (ISO 8601 with 'Z' as UTC indicator)
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        zonedDateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String dateTimeString = node.asText();

        // Attempt to parse as ZonedDateTime first (includes time zone)
        for (DateTimeFormatter formatter : zonedDateTimeFormatters) {
            try {
                return ZonedDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e) {
                // Continue to try the next format
            }
        }

        // If ZonedDateTime parsing fails, attempt to parse as LocalDateTime
        for (DateTimeFormatter formatter : localDateTimeFormatters) {
            try {
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e) {
                // Continue to try the next format
            }
        }

        throw new RuntimeException("Failed to parse date: " + dateTimeString);
    }
}
