package model.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportMetaTest {
    @Test
    public void invalidTest() {
        //claims branches missing, probably to do in order but it's covered
        ReportMeta reportMeta = new ReportMeta();
        assertFalse(reportMeta.isValid());
        reportMeta.setTestTime(0.0);
        assertFalse(reportMeta.isValid());
        reportMeta.setTestUsedSteps(0.0);
        assertFalse(reportMeta.isValid());
        reportMeta.setTestPathWeight(0.0);
        assertFalse(reportMeta.isValid());
        reportMeta.setTestMaxSteps(0L);
        assertFalse(reportMeta.isValid());
        reportMeta.setTestSpace(0.0);
        assertFalse(reportMeta.isValid());
        reportMeta.setAlgorithmImplementationType("");
        assertFalse(reportMeta.isValid());
        reportMeta.setAlgorithmImplementationType("a");
        assertFalse(reportMeta.isValid());
        reportMeta.setAlgorithmName( "");
        assertFalse(reportMeta.isValid());
        reportMeta.setAlgorithmName("v");
        assertTrue(reportMeta.isValid());
    }
}
