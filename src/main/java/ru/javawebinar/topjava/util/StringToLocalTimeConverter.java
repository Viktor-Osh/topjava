package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private static final String TIME_PATTERN = "HH:mm";

    public String getTimePattern() {
        return TIME_PATTERN;
    }

    @Override
    public LocalTime convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(TIME_PATTERN));
    }
}
