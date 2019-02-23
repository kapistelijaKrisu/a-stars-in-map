
package search_algorithm;

import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import search_algorithm.structure_type.StackType;
import search_algorithm.structure_type.UniqueSetType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DepthAlgorithmTest {

    private DepthSearch depthSearch;
    private MockAnalysisWriter mockWriter;

    @BeforeEach
    public void setUp() {
        mockWriter = new MockAnalysisWriter();
        depthSearch = new DepthSearch(mockWriter, StackType.CUSTOM_STACK, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET);
    }

    @Test
    public void doesNotThrowErrorsWithValidMapTest() throws IOException {
        depthSearch.setMapClean(WebMapMock.getMinimumValidMap());
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Depth first", mockWriter.getReceivedPath());
    }

    @Test
    public void doesThrowErrorsWithInvalidMapTest() throws IOException {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(-1, 22);
        depthSearch.setMapClean(invalidMap);
        try {
            depthSearch.runSearch();
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
        depthSearch.setMapClean(map);
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Depth first", mockWriter.getReceivedPath());
        assertEquals("Depth first with CUSTOM_DYNAMIC_SIZE_HASH_SET to keep track of visited edges and CUSTOM_STACK as an implementation of stack.", mockWriter.receivedAlDoc());
        assertEquals("Depth first", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | )", mockWriter.receivedAlTime());
        assertEquals("Width: 2 Height: 2\r\nStart location: 1,1\r\nTarget location: 1,1", mockWriter.receivedMapInfo());
        assertEquals("1", mockWriter.receivedTestMaxSteps());
        assertEquals("Target was not found", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = ". @ \r\n@ O ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }

    @Test
    public void valuesSetCorectlyWhereStartIsNotTargetTest() throws IOException {
        depthSearch.setMapClean(WebMapMock.getValid6x7Map());
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Depth first", mockWriter.getReceivedPath());
        assertEquals("Depth first with CUSTOM_DYNAMIC_SIZE_HASH_SET to keep track of visited edges and CUSTOM_STACK as an implementation of stack.", mockWriter.receivedAlDoc());
        assertEquals("Depth first", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | )", mockWriter.receivedAlTime());
        assertEquals("Width: 7 Height: 6\r\nStart location: 0,1\r\nTarget location: 4,3", mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("28", mockWriter.receivedTestPathWeight());
        assertEquals("21", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap =
                "X X X @ ! ! ! \r\n" +
                "S @ X X X X X \r\n" +
                "! @ ! @ ! ! X \r\n" +
                ". @ . @ F X X \r\n" +
                ". . . @ . ! ! \r\n" +
                ". . . . . . . ";
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }
}

