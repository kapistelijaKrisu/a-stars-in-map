package search_algorithm;

import model.web.WebMap;
import model.web.WeightedPoint;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AnalysableAlgorithmTest {
    @Test
    public void cleansPathWithNewMap() {
        var algorithm = mock(AnalysableAlgorithm.class, CALLS_REAL_METHODS);
        var webMap = mock(WebMap.class);
        when(webMap.isValid()).thenReturn(true);

        algorithm.setReportFilePath("to be cleaned");
        algorithm.setMapClean(webMap);

        assertEquals(null, algorithm.getReportFilePath());
        assertEquals(webMap, algorithm.getMap());
    }

    @Test
    public void nullIsIllegalArgumentTest() {
        Throwable exceptionZero = assertThrows(IllegalArgumentException.class, () -> new AnalysableAlgorithm(null) {
            @Override
            protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {

            }

            @Override
            public String getTheoreticalTime() {
                return null;
            }

            @Override
            public String getTheoreticalSpace() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        assertEquals("analysis writer cannot be null", exceptionZero.getMessage());
    }

    @Test
    public void throwsErrorIfAnalysisWriterDoes() {
        Throwable exceptionZero = assertThrows(IllegalArgumentException.class, () -> new AnalysableAlgorithm(null) {
            @Override
            protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {

            }

            @Override
            public String getTheoreticalTime() {
                return null;
            }

            @Override
            public String getTheoreticalSpace() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        assertEquals("analysis writer cannot be null", exceptionZero.getMessage());
    }
}
