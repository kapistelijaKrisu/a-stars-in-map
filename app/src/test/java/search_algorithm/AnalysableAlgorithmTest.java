package search_algorithm;

import file_operations.analysis_writer.AnalysisWriter;
import model.web.WebMap;
import model.web.WeightedPoint;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnalysableAlgorithmTest {

    @Test
    public void cleansPathWithNewMap() {
        var algorithm = mock(AnalysableAlgorithm.class, CALLS_REAL_METHODS);
        var webMap = mock(WebMap.class);
        when(webMap.isValid()).thenReturn(true);

        algorithm.setReportFilePath("to be cleaned");
        algorithm.setMapClean(webMap);

        //since not all values are filled should return null
        assertNull(algorithm.getReportFilePath());
        assertEquals(webMap, algorithm.getMap());
    }

    @Test
    public void nullWriterIsIllegalArgumentTest() {
        Throwable exceptionZero = assertThrows(IllegalArgumentException.class, () -> new AnalysableAlgorithm(null, "name") {
            @Override
            protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
            }
            @Override
            public String getShortImpl() {
                return null;
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
    public void nullNameIsIllegalArgumentTest() {
        Throwable exceptionZero = assertThrows(IllegalArgumentException.class, () -> new AnalysableAlgorithm(new AnalysisWriter(), null) {
            @Override
            protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
            }
            @Override
            public String getShortImpl() {
                return null;
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
        assertEquals("name needs to be a valid filename", exceptionZero.getMessage());
    }
    @Test
    public void illegalNameIsIllegalArgumentTest() {
        Throwable exceptionZero = assertThrows(IllegalArgumentException.class, () -> new AnalysableAlgorithm(new AnalysisWriter(), ":") {
            @Override
            protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {
            }
            @Override
            public String getShortImpl() {
                return null;
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
        assertEquals("name needs to be a valid filename", exceptionZero.getMessage());
    }

    @Test
    public void throwsErrorIfAnalysisWriterNull() {
        Throwable exceptionZero = assertThrows(IllegalArgumentException.class, () -> new AnalysableAlgorithm(null, "name") {
            @Override
            protected void searchAlgorithm(long timeOfStart, long availableSpace, Map<WeightedPoint, WeightedPoint> fromToNodeSet) {

            }
            @Override
            public String getShortImpl() {
                return null;
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
