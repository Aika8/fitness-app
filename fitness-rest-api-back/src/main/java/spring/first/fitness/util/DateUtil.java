package spring.first.fitness.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private DateUtil() { throw new IllegalStateException("Utility class"); }

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

    public static boolean isValidDate(String date) {
        return isValidDate(date, DEFAULT_DATE_PATTERN);
    }

    public static boolean isValidDate(String date, String pattern) {
        try {
            new SimpleDateFormat(pattern).parse(date);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String formatDateTime(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.format(formatter);
    }

    public static LocalDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        return LocalDateTime.parse(dateString, formatter);
    }
}
