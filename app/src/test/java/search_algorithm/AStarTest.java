package search_algorithm;

import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import search_algorithm.structure_type.DistanceMapType;
import search_algorithm.structure_type.HeapType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AStarTest {
    private AStar aStar;
    private MockAnalysisWriter mockWriter;

    @BeforeEach
    public void setUp() {
        mockWriter = new MockAnalysisWriter();
        aStar = new AStar(mockWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_1D);
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
        invalidMap.setTileTarget(-1, 22);
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
     *
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
        assertEquals("A Star with ARRAY_1D to keep track of known distances and CUSTOM_MIN_HEAP as an implementation of min heap.", mockWriter.receivedAlDoc());
        assertEquals("A Star", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("Width: 2 Height: 2\r\nStart location: 1,1\r\nTarget location: 1,1", mockWriter.receivedMapInfo());
        assertEquals("1", mockWriter.receivedTestMaxSteps());
        assertEquals("Target was not found", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = ". @ \r\n@ O ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }

    @Test
    public void valuesSetCorectlyWhereStartIsNotTargetTest() throws IOException {
        aStar.setMapClean(WebMapMock.getValid6x7Map());
        aStar.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/A Star", mockWriter.getReceivedPath());
        assertEquals("A Star with ARRAY_1D to keep track of known distances and CUSTOM_MIN_HEAP as an implementation of min heap.", mockWriter.receivedAlDoc());
        assertEquals("A Star", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("Width: 7 Height: 6\r\nStart location: 0,1\r\nTarget location: 4,3", mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("16", mockWriter.receivedTestPathWeight());
        assertEquals("13", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap =
                "X X X @ ! . . \r\n" +
                        "S @ X X X ! . \r\n" +
                        "! @ ! @ X . . \r\n" +
                        "! @ . @ F . . \r\n" +
                        ". . . @ . . . \r\n" +
                        ". . . . . . . ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }
}

