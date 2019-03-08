package system_tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NanoSecondPrettifiedTest {

    @Test
    public void testZero(){
        assertEquals("0", NanoSecondPrettified.prettifyNanoSeconds(0L));
    }

    @Test
    public void testOnlyNanos(){
        assertEquals("123456ns", NanoSecondPrettified.prettifyNanoSeconds(123456L));
    }

    @Test
    public void testNanoAndMilliZero(){
        assertEquals("9ms 123456ns", NanoSecondPrettified.prettifyNanoSeconds(9123456L));
    }

    @Test
    public void testSecMilliNano(){
        assertEquals("8sec 999ms 123456ns", NanoSecondPrettified.prettifyNanoSeconds(8999123456L));
    }
}
