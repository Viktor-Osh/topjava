package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T ldt, T start, T end) {
        return ldt.compareTo(start) >= 0 && ldt.compareTo(end) < 0;
    }

    public static LocalDate convertToDate(String date) {
        return date.isEmpty() ? null : LocalDate.parse(date);
    }

    public static LocalTime convertToTime(String time) {
        return time.isEmpty() ? null : LocalTime.parse(time);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

