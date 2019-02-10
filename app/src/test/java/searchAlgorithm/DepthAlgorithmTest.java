
package searchAlgorithm;

import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DepthAlgorithmTest {

    private DepthSearch depthSearch;
    private MockAnalysisWriter mockWriter;

    @BeforeEach
    public void setUp() {
        mockWriter = new MockAnalysisWriter();
        depthSearch = new DepthSearch(mockWriter);
    }

    @Test
    public void doesNotThrowErrorsWithValidMapTest() throws IOException {
        depthSearch.setMapClean(WebMapMock.getMinimumValidMap());
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/depth search", mockWriter.getReceivedPath());
    }

    @Test
    public void doesThrowErrorsWithInvalidMapTest() throws IOException {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(new Point(-1, 22));
        depthSearch.setMapClean(invalidMap);
        try {
            depthSearch.runSearch();
            assertTrue(false);
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
        map.setTileStart(map.getTileTarget());
        depthSearch.setMapClean(map);
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/depth search", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("depth search", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | )", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("0", mockWriter.receivedTestMaxSteps());
        assertEquals("0", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = "# # \r\n# O ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }

    @Test
    public void valuesSetCorectlyWhereStartIsNotTargetTest() throws IOException {
        depthSearch.setMapClean(WebMapMock.getValid6x7Map());
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/depth search", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("depth search", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | )", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("11", mockWriter.receivedTestPathWeight());
        assertEquals("21", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = "X X X # v v v \r\n" +
                "S # X X X X X \r\n" +
                "v # v # v v X \r\n" +
                ". # . # T X X \r\n" +
                ". . . # . v v \r\n" +
                ". . . . . . . ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }
}

