package model.report;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    public void validationTest() {
        Report report = new Report();
        report.setValueOf(ReportCodeKey.ALGORITHM_NAME, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.CPU, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.COMPILER, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.RUNTIME, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.VM_NAME, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.VM_VERSION, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.ENV_HEAP, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.MAP_INFO, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.THEORY_TIME_COMPLEXITY, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.THEORY_SPACE_COMPLEXITY, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.IMPLEMENTATION_INFO, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.TIME_USED, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.SPACE_USED, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.MAX_STEPS, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.PATH_WEIGHT, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.PROCESSED_MAP, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.STEPS_USED, "");
        assertFalse(report.isValid());
        report.setValueOf(ReportCodeKey.OS, "");
        assertTrue(report.isValid());
    }

    @Test
    public void setterAndGetterTest() {
        Report report = new Report();
        report.setValueOf(ReportCodeKey.ALGORITHM_NAME, "a");
        assertEquals("a", report.getValueOf(ReportCodeKey.ALGORITHM_NAME));
        report.setValueOf(ReportCodeKey.CPU, "b");
        assertEquals("b", report.getValueOf(ReportCodeKey.CPU));
        report.setValueOf(ReportCodeKey.COMPILER, "c");
        assertEquals("c", report.getValueOf(ReportCodeKey.COMPILER));
        report.setValueOf(ReportCodeKey.RUNTIME, "d");
        assertEquals("d", report.getValueOf(ReportCodeKey.RUNTIME));
        report.setValueOf(ReportCodeKey.VM_NAME, "e");
        assertEquals("e", report.getValueOf(ReportCodeKey.VM_NAME));
        report.setValueOf(ReportCodeKey.VM_VERSION, "f");
        assertEquals("f", report.getValueOf(ReportCodeKey.VM_VERSION));
        report.setValueOf(ReportCodeKey.ENV_HEAP, "g");
        assertEquals("g", report.getValueOf(ReportCodeKey.ENV_HEAP));
        report.setValueOf(ReportCodeKey.MAP_INFO, "h");
        assertEquals("h", report.getValueOf(ReportCodeKey.MAP_INFO));
        report.setValueOf(ReportCodeKey.THEORY_TIME_COMPLEXITY, "i");
        assertEquals("i", report.getValueOf(ReportCodeKey.THEORY_TIME_COMPLEXITY));
        report.setValueOf(ReportCodeKey.THEORY_SPACE_COMPLEXITY, "j");
        assertEquals("j", report.getValueOf(ReportCodeKey.THEORY_SPACE_COMPLEXITY));
        report.setValueOf(ReportCodeKey.IMPLEMENTATION_INFO, "q");
        assertEquals("q", report.getValueOf(ReportCodeKey.IMPLEMENTATION_INFO));
        report.setValueOf(ReportCodeKey.TIME_USED, "l");
        assertEquals("l", report.getValueOf(ReportCodeKey.TIME_USED));
        report.setValueOf(ReportCodeKey.SPACE_USED, "m");
        assertEquals("m", report.getValueOf(ReportCodeKey.SPACE_USED));
        report.setValueOf(ReportCodeKey.MAX_STEPS, "o");
        assertEquals("o", report.getValueOf(ReportCodeKey.MAX_STEPS));
        report.setValueOf(ReportCodeKey.PATH_WEIGHT, "p");
        assertEquals("p", report.getValueOf(ReportCodeKey.PATH_WEIGHT));
        report.setValueOf(ReportCodeKey.PROCESSED_MAP, "q");
        assertEquals("q", report.getValueOf(ReportCodeKey.PROCESSED_MAP));
        report.setValueOf(ReportCodeKey.STEPS_USED, "r");
        assertEquals("r", report.getValueOf(ReportCodeKey.STEPS_USED));
        report.setValueOf(ReportCodeKey.OS, "s");
        assertEquals("s", report.getValueOf(ReportCodeKey.OS));
    }

    @Test
    public void throwsErrorIfNoSuchKeyTest() {
        Report report = new Report();
        Throwable exceptionNull = assertThrows(IllegalArgumentException.class, () -> report.setValueOf(null, "a"));
        assertEquals("No such value in report!", exceptionNull.getMessage());

    }

    @Test
    public void valuesToMapTest() {
        Report report = new Report();
        var resultingMap = report.valuesToMap();
        assertEquals(ReportCodeKey.values().length, resultingMap.entrySet().size());
    }
}
