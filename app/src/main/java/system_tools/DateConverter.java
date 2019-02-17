package system_tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * converts time objects into a string format
 */
public class DateConverter {

    private final DateTimeFormatter dateTimeFormatter;

    /**
     * creates internal formatter of pattern dd-MM-yyyy HH-mm-ss
     */
    public DateConverter() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
    }

    /**
     *
     * @param localDateTime object to be formatted
     * @return String representation of @param
     * @throws NullPointerException if param is null
     */
    public String getDateAsString(LocalDateTime localDateTime) throws NullPointerException {
        if (localDateTime == null) throw new NullPointerException("Given null as LocalDateTime");
        return dateTimeFormatter.format(localDateTime);
    }
}
