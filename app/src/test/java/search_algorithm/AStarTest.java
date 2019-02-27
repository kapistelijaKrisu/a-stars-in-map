package search_algorithm;

import mock.SystemLine;
import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import search_algorithm.structure_type.DistanceMapType;
import search_algorithm.structure_type.HeapType;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AStarTest {

    private static MockAnalysisWriter mockWriter = new MockAnalysisWriter();

    @Test
    public void nullIsIllegalArgumentTest() {
        Throwable exceptionNull = assertThrows(IllegalArgumentException.class, () -> new AStar(mockWriter, null, DistanceMapType.ARRAY_1D));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
        exceptionNull = assertThrows(IllegalArgumentException.class, () -> new AStar(mockWriter, HeapType.PRE_MADE_MIN_HEAP, null));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
        exceptionNull = assertThrows(IllegalArgumentException.class, () -> new AStar(mockWriter, null, null));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
    }

    //setting up tests for all non null constructors
    static class AStarArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    new AStar(mockWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_2D),
                    new AStar(mockWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_2D),
                    new AStar(mockWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_1D),
                    new AStar(mockWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_1D)
            ).map(Arguments::of);
        }
    }


    @ParameterizedTest
    @ArgumentsSource(AStarArgumentsProvider.class)
    public void doesNotThrowErrorsWithValidMapTest(AStar aStar) throws IOException {
        aStar.setMapClean(WebMapMock.getMinimumValidMap());
        aStar.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/A Star", mockWriter.getReceivedPath());
    }

    @ParameterizedTest
    @ArgumentsSource(AStarArgumentsProvider.class)
    public void doesThrowErrorsWithInvalidMapTest(AStar aStar) {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(-1, 22);
        aStar.setMapClean(invalidMap);

        Throwable exception = assertThrows(IllegalStateException.class, () -> aStar.runSearch());
        assertEquals("Requires valid map, and name for algorithm", exception.getMessage());
    }

    /**
     * tests all but hardware and jvm dependant values
     *
     * @throws IOException shouldn't happen because writer is mocked
     */
    @ParameterizedTest
    @ArgumentsSource(AStarArgumentsProvider.class)
    public void valuesSetCorectlyWhereStartIsTargetTest(AStar aStar) throws IOException {
        WebMap map = WebMapMock.getMinimumValidMap();
        map.setTileStart(map.getTileTarget().x, map.getTileTarget().y);
        aStar.setMapClean(map);
        aStar.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/A Star", mockWriter.getReceivedPath());
        assertEquals("A Star with " + aStar.getDistanceMapType() + " to keep track of known distances and " + aStar.getHeapType() + " as an implementation of min heap.", mockWriter.receivedAlDoc());
        assertEquals("A Star", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("Width: 2 Height: 2\r\nStart location: 1,1\r\nTarget location: 1,1", mockWriter.receivedMapInfo());
        assertEquals("1", mockWriter.receivedTestMaxSteps());
        assertEquals("Target was not found", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = SystemLine.breakLine(". @ \r\n@ O ");
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }

    @ParameterizedTest
    @ArgumentsSource(AStarArgumentsProvider.class)
    public void valuesSetCorectlyWhereStartIsNotTargetTest(AStar aStar) throws IOException {
        aStar.setMapClean(WebMapMock.getValid6x7Map());
        aStar.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/A Star", mockWriter.getReceivedPath());
        assertEquals("A Star with " + aStar.getDistanceMapType() + " to keep track of known distances and " + aStar.getHeapType() + " as an implementation of min heap.", mockWriter.receivedAlDoc());
        assertEquals("A Star", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals("Width: 7 Height: 6\r\nStart location: 0,1\r\nTarget location: 4,3", mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("16", mockWriter.receivedTestPathWeight());
        assertEquals("13", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap =
                SystemLine.breakLine("X X X @ ! . . \r\n" +
                        "S @ X X X ! . \r\n" +
                        "! @ ! @ X . . \r\n" +
                        "! @ . @ F . . \r\n" +
                        ". . . @ . . . \r\n" +
                        ". . . . . . . ");
        assertEquals(expectedProcessedMap, mockWriter.receivedProcessedMap());
    }
}

