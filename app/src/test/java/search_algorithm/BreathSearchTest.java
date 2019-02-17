package search_algorithm;
import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BreathSearchTest {

    private BreathSearch breathSearch;
    private MockAnalysisWriter mockWriter;

    @BeforeEach
    public void setUp() {
        mockWriter = new MockAnalysisWriter();
        breathSearch = new BreathSearch(mockWriter);
    }

    @Test
    public void doesNotThrowErrorsWithValidMapTest() throws IOException {
        breathSearch.setMapClean(WebMapMock.getMinimumValidMap());
        breathSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/breath width", mockWriter.getReceivedPath());
    }

    @Test
    public void doesThrowErrorsWithInvalidMapTest() throws IOException {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(-1,22);
        breathSearch.setMapClean(invalidMap);
        try {
            breathSearch.runSearch();
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
        breathSearch.setMapClean(map);
        breathSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/breath width", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("breath width", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | )", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("1", mockWriter.receivedTestMaxSteps());
        assertEquals("0", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = ". # \r\n# O ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }

    @Test
    public void valuesSetCorectlyWhereStartIsNotTargetTest() throws IOException {
        breathSearch.setMapClean(WebMapMock.getValid6x7Map());
        breathSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/breath width", mockWriter.getReceivedPath());
        assertEquals("TBD", mockWriter.receivedAlDoc());
        assertEquals("breath width", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | )", mockWriter.receivedAlTime());
        assertEquals("TBD", mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("16", mockWriter.receivedTestPathWeight());
        assertEquals("27", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap =
                "X X X # v v . \r\n" +
                "S # X X X v v \r\n" +
                "v # v # X v . \r\n" +
                "v # v # F . . \r\n" +
                "v v v # v . . \r\n" +
                "v v v v v v . ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }
}
