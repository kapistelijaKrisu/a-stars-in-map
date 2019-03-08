package system_tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineBreakerTest {

    @Test
    public void stringRemainsSameTest() {
        String noLineString = " Test string without line breaks. :D";
        assertEquals(" Test string without line breaks. :D", LineBreaker.formatBreaks(noLineString));
    }

    @Test
    public void stringRemainsSameWindowsTest() {
        String lineBreakWindows = "\r\n";
        assertEquals(System.lineSeparator(), LineBreaker.formatBreaks(lineBreakWindows));
    }

    @Test
    public void stringRemainsSameUnixTest() {
        String lineBreakUnix = "\n";
        assertEquals(System.lineSeparator(), LineBreaker.formatBreaks(lineBreakUnix));
    }

    @Test
    public void nullRemainsNullTest() {
        assertEquals(null, LineBreaker.formatBreaks(null));
    }
}

