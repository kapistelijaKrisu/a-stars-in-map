package search_algorithm;

import model.web.WebMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AnalysableAlgorithmTest {
    @Test
    public void cleansPathWithNewMap() {
        var algorithm = mock(AnalysableAlgorithm.class, CALLS_REAL_METHODS);
        var webmap = mock(WebMap.class);
        when(webmap.isValid()).thenReturn(true);

        algorithm.setReportFilePath("to be cleaned");
        algorithm.setMapClean(webmap);

        assertEquals(null, algorithm.getReportFilePath());
        assertEquals(webmap, algorithm.getMap());
    }
}