package search_algorithm;

import mock.MockAnalysisWriter;
import mock.SystemLine;
import mock.WebMapMock;
import model.report.ReportMeta;
import model.structure.structure_type_enum.DistanceMapType;
import model.structure.structure_type_enum.HeapType;
import model.web.WebMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {
    private static MockAnalysisWriter mockWriter = new MockAnalysisWriter();

    @Test
    public void nullIsIllegalArgumentTest() {
        Throwable exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Dijkstra(mockWriter, null, DistanceMapType.ARRAY_1D));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
        exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Dijkstra(mockWriter, HeapType.PRE_MADE_MIN_HEAP, null));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
        exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Dijkstra(mockWriter, null, null));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
    }

    //setting up tests for all non null constructors
    static class DijkstraArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    new Dijkstra(mockWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_1D),
                    new Dijkstra(mockWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_1D),
                    new Dijkstra(mockWriter, HeapType.PRE_MADE_MIN_HEAP, DistanceMapType.ARRAY_2D),
                    new Dijkstra(mockWriter, HeapType.CUSTOM_MIN_HEAP, DistanceMapType.ARRAY_2D)
            ).map(Arguments::of);
        }
    }


    @ParameterizedTest
    @ArgumentsSource(DijkstraArgumentsProvider.class)
    public void doesNotThrowErrorsWithValidMapTest(Dijkstra dijkstra) throws IOException {
        dijkstra.setMapClean(WebMapMock.getMinimumValidMap());
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Dijkstra", mockWriter.getReceivedPath());
    }

    @ParameterizedTest
    @ArgumentsSource(DijkstraArgumentsProvider.class)
    public void doesThrowErrorsWithInvalidMapTest(Dijkstra dijkstra) {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(-1, 22);
        dijkstra.setMapClean(invalidMap);

        Throwable exception = assertThrows(IllegalStateException.class, () -> dijkstra.runSearch());
        assertEquals("Requires valid map, and name for algorithm", exception.getMessage());
    }

    /**
     * tests  but all hardware related untestable values
     *
     * @throws IOException shouldnt happen because writer is mocked
     */
    @ParameterizedTest
    @ArgumentsSource(DijkstraArgumentsProvider.class)
    public void valuesSetCorectlyWhereStartIsTargetTest(Dijkstra dijkstra) throws IOException {
        WebMap map = WebMapMock.getMinimumValidMap();
        map.setTileStart(map.getTileTarget().x, map.getTileTarget().y);
        dijkstra.setMapClean(map);
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Dijkstra", mockWriter.getReceivedPath());
        assertEquals("Dijkstra with " + dijkstra.getDistanceMapType().getTextValue() + " as distance tracker and " + dijkstra.getHeapType().getTextValue() + " as min heap.", mockWriter.receivedAlDoc());
        assertEquals("Dijkstra", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals(SystemLine.breakLine("Width: 2 Height: 2\r\nStart location: 1,1\r\nTarget location: 1,1"), mockWriter.receivedMapInfo());
        assertEquals("1", mockWriter.receivedTestMaxSteps());
        assertEquals("Target was not found", mockWriter.receivedTestPathWeight());
        assertEquals("0", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap = ". @ \r\n@ O";
        assertEquals(SystemLine.breakLine(expectedProcessedMap), mockWriter.receivedProcessedMap());
    }

    @ParameterizedTest
    @ArgumentsSource(DijkstraArgumentsProvider.class)
    public void valuesSetCorrectlyWhereStartIsNotTargetTest(Dijkstra dijkstra) throws IOException {
        dijkstra.setMapClean(WebMapMock.getValid6x7Map());
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Dijkstra", mockWriter.getReceivedPath());
        assertEquals("Dijkstra with " + dijkstra.getDistanceMapType().getTextValue() + " as distance tracker and " + dijkstra.getHeapType().getTextValue() + " as min heap.", mockWriter.receivedAlDoc());
        assertEquals("Dijkstra", mockWriter.receivedAlgorithm());
        assertEquals("| V |", mockWriter.receivedAlSpace());
        assertEquals("O( | V + E | log | V |)", mockWriter.receivedAlTime());
        assertEquals(SystemLine.breakLine("Width: 7 Height: 6\r\nStart location: 0,1\r\nTarget location: 4,3"), mockWriter.receivedMapInfo());
        assertEquals("34", mockWriter.receivedTestMaxSteps());
        assertEquals("16", mockWriter.receivedTestPathWeight());
//        assertEquals("22", mockWriter.receivedTestUsedSteps());

        String expectedProcessedMap =
                "X X X @ ! ! ! \r\n" +
                        "S @ X X X ! ! \r\n" +
                        "! @ ! @ X ! . \r\n" +
                        "! @ ! @ F . . \r\n" +
                        "! ! ! @ . . . \r\n" +
                        "! . . . . . .";
        assertEquals(SystemLine.breakLine(expectedProcessedMap), mockWriter.receivedProcessedMap());
    }

    @ParameterizedTest
    @ArgumentsSource(DijkstraArgumentsProvider.class)
    public void metaValuesSetCorrectlyWhereStartIsTargetTest(Dijkstra dijkstra) throws IOException {
        WebMap map = WebMapMock.getMinimumValidMap();
        map.setTileStart(map.getTileTarget().x, map.getTileTarget().y);
        dijkstra.setMapClean(map);
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        ReportMeta producedMeta = mockWriter.getReceivedReportMeta();

        assertEquals("Dijkstra", producedMeta.getAlgorithmName());
        assertEquals("Distances: " + dijkstra.getDistanceMapType().getTextValue() + ", Min heap: " + dijkstra.getHeapType().getTextValue(), producedMeta.getAlgorithmImplementationType());
        assertEquals(0, producedMeta.getTestPathWeight().doubleValue());
        assertTrue(0 < producedMeta.getTestTime());
        assertNotNull(producedMeta.getTestSpace());
        assertEquals(1, producedMeta.getTestMaxSteps().longValue());
        assertEquals(0, producedMeta.getTestUsedSteps().doubleValue());



    }

    @ParameterizedTest
    @ArgumentsSource(DijkstraArgumentsProvider.class)
    public void metaSetCorrectlyWhereStartIsNotTargetTest(Dijkstra dijkstra) throws IOException {
        dijkstra.setMapClean(WebMapMock.getValid6x7Map());
        dijkstra.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        ReportMeta producedMeta = mockWriter.getReceivedReportMeta();

        assertEquals("Dijkstra", producedMeta.getAlgorithmName());
        assertEquals("Distances: " + dijkstra.getDistanceMapType().getTextValue() + ", Min heap: " + dijkstra.getHeapType().getTextValue(), producedMeta.getAlgorithmImplementationType());
        assertEquals(16, producedMeta.getTestPathWeight().doubleValue());
        assertTrue(0 < producedMeta.getTestTime());
        assertNotNull(producedMeta.getTestSpace());
        assertEquals(34, producedMeta.getTestMaxSteps().longValue());
        assertEquals(22, producedMeta.getTestUsedSteps().doubleValue());
    }
}

