package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return s.length() == 0 ? null : LocalTime.parse(s);
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return time == null ? "" : time.toString();
    }
}
