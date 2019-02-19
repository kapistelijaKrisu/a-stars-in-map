package search_algorithm;

import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraTest {
    private Dijkstra dijkstra;
    private MockAnalysisWriter mockWriter;

    @BeforeEach
    public void setUp() {
        mockWriter = new MockAnalysisWriter();
        dijkstra = new Dijkstra(mockWriter);
    }

    @Test
    public void doesNotThrowErrorsWithValidMapTest() throws IOException {
        dijkstra.setMapClean(WebMapMock.getMinimumValidMap());
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Dijkstra", mockWriter.getReceivedPath());
    }

    @Test
    public void doesThrowErrorsWithInvalidMapTest() throws IOException {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(-1,22);
        dijkstra.setMapClean(invalidMap);
        try {
            dijkstra.runSearch();
            fail("Test failed by reaching this part of code");
        } catch (IllegalStateException e) {
            assertFalse(mockWriter.isValidatingReturnedTrue());
        }
    }

    /**
     * tests all hardware related untestable values
     * @throws IOException shouldnt happen because writer is mocked
     */
    @Test
    public void valuesSetCorectlyWhereStartIsTargetTest() throws IOException {
        WebMap map = WebMapMock.getMinimumValidMap();
        map.setTileStart(map.getTileTarget().x, map.getTileTarget().y);
        dijkstra.setMapClean(map);
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Dijkstra", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("Dijkstra", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("1", mockWriter.receivedTestMaxSteps());
        assertEquals("0", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = ". @ \r\n@ O ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }

    @Test
    public void valuesSetCorectlyWhereStartIsNotTargetTest() throws IOException {
        dijkstra.setMapClean(WebMapMock.getValid6x7Map());
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Dijkstra", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("Dijkstra", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("16", mockWriter.receivedTestPathWeight());
        assertEquals("22", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap =
                "X X X @ ! ! ! \r\n" +
                        "S @ X X X ! ! \r\n" +
                        "! @ ! @ X ! . \r\n" +
                        "! @ ! @ F . . \r\n" +
                        "! ! ! @ . . . \r\n" +
                        "! . . . . . . ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }
}

