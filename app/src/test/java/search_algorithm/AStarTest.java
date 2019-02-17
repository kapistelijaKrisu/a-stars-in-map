package search_algorithm;

import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AStarTest {private AStar aStar;
    private MockAnalysisWriter mockWriter;

    @BeforeEach
    public void setUp() {
        mockWriter = new MockAnalysisWriter();
        aStar = new AStar(mockWriter);
    }

    @Test
    public void doesNotThrowErrorsWithValidMapTest() throws IOException {
        aStar.setMapClean(WebMapMock.getMinimumValidMap());
        aStar.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/A Star", mockWriter.getReceivedPath());
    }

    @Test
    public void doesThrowErrorsWithInvalidMapTest() throws IOException {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(-1,22);
        aStar.setMapClean(invalidMap);
        try {
            aStar.runSearch();
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
        aStar.setMapClean(map);
        aStar.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/A Star", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("A Star", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("1", mockWriter.receivedTestMaxSteps());
        assertEquals("0", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = ". # \r\n# O ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }

    @Test
    public void valuesSetCorectlyWhereStartIsNotTargetTest() throws IOException {
        aStar.setMapClean(WebMapMock.getValid6x7Map());
        aStar.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/A Star", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("A Star", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("16", mockWriter.receivedTestPathWeight());
        assertEquals("13", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap =
                "X X X # v v . \r\n" +
                        "S # X X X v . \r\n" +
                        "v # v # X . . \r\n" +
                        ". # . # F . . \r\n" +
                        ". . . # . . . \r\n" +
                        ". . . . . . . ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }
}

