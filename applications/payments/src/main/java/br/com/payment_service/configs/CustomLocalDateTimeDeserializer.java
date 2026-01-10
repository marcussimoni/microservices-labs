package br.com.payment_service.configs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z"), // Fri Oct 17 2025 07:28:09 GMT+0000
            DateTimeFormatter.ISO_OFFSET_DATE_TIME, // 2025-10-17T14:24:34.843Z
            DateTimeFormatter.ISO_LOCAL_DATE,       // 2025-10-17
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), // optional
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateStr = p.getText();

        // Remove parentheses part if present
        dateStr = dateStr.replaceAll(" \\(.*\\)$", "");

        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                if (formatter == DateTimeFormatter.ISO_LOCAL_DATE) {
                    // Only date provided, set time to start of day
                    LocalDate localDate = LocalDate.parse(dateStr, formatter);
                    return localDate.atStartOfDay();
                }

                // Try parsing as ZonedDateTime first to handle offsets
                try {
                    ZonedDateTime zdt = ZonedDateTime.parse(dateStr, formatter);
                    return zdt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                } catch (DateTimeParseException e) {
                    // fallback to LocalDateTime parse
                    return LocalDateTime.parse(dateStr, formatter);
                }

            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IOException("Unable to parse date: " + dateStr);
    }
}