package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public String getDatePattern() {
        return DATE_PATTERN;
    }

    @Override
    public LocalDate convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}
