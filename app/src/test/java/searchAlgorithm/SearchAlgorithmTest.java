package searchAlgorithm;

import model.web.WebMap;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SearchAlgorithmTest {
    @Test
    public void cleansPathWithNewMap() {
        var algorithm = mock(SearchAlgorithm.class, Mockito.CALLS_REAL_METHODS);
        var webmap = mock(WebMap.class);
        when(webmap.isValid()).thenReturn(true);

        algorithm.setReportFilePath("to be cleaned");
        algorithm.setMapClean(webmap);

        assertEquals(null, algorithm.getReportFilePath());
        assertEquals(webmap, algorithm.getMap());
    }
}
