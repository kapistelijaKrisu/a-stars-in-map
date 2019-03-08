package system_tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * converts time objects into a string format
 */
public class DateConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss.SSS");

    /**
     * @param localDateTime object to be formatted
     * @return String representation of @param
     * @throws NullPointerException if param is null
     */
    public static String getDateAsString(LocalDateTime localDateTime) throws IllegalArgumentException {
        if (localDateTime == null) throw new IllegalArgumentException("Given null as LocalDateTime");
        return dateTimeFormatter.format(localDateTime);
    }
}
