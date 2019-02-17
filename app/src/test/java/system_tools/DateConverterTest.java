package system_tools;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateConverterTest {

    @Test
    public void basicTest() {
        DateConverter dateConverter = new DateConverter();
        LocalDateTime testTime = LocalDateTime.of(2003, 12, 1, 16, 20, 10, 5);
        var conversion = dateConverter.getDateAsString(testTime);
        assertEquals("01-12-2003 16-20-10", conversion);
    }

    @Test
    public void nullExceptionTest() {
        DateConverter dateConverter = new DateConverter();
        Throwable exception = assertThrows(NullPointerException.class, () -> dateConverter.getDateAsString(null));
        assertEquals("Given null as LocalDateTime", exception.getMessage());
    }
}
