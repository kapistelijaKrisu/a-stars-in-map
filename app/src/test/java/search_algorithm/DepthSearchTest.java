package search_algorithm;

import mock.MockAnalysisWriter;
import mock.WebMapMock;
import model.web.WebMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import search_algorithm.structure_type.StackType;
import search_algorithm.structure_type.UniqueSetType;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DepthSearchTest {

    private static MockAnalysisWriter mockWriter = new MockAnalysisWriter();

    @Test
    public void nullIsIllegalArgumentTest() {
        Throwable exceptionNull = assertThrows(IllegalArgumentException.class, () -> new DepthSearch(mockWriter, null, UniqueSetType.PRE_MADE_HASH_SET));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
        exceptionNull = assertThrows(IllegalArgumentException.class, () -> new DepthSearch(mockWriter, StackType.PRE_MADE_STACK, null));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
        exceptionNull = assertThrows(IllegalArgumentException.class, () -> new DepthSearch(mockWriter, null, null));
        assertEquals("Arguments cannot be null", exceptionNull.getMessage());
    }

    //setting up tests for all non null constructors
    static class DepthSearchArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    new DepthSearch(mockWriter, StackType.PRE_MADE_STACK, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET),
                    new DepthSearch(mockWriter, StackType.PRE_MADE_STACK, UniqueSetType.PRE_MADE_HASH_SET),
                    new DepthSearch(mockWriter, StackType.PRE_MADE_STACK, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET),
                    new DepthSearch(mockWriter, StackType.CUSTOM_STACK, UniqueSetType.CUSTOM_DYNAMIC_SIZE_HASH_SET),
                    new DepthSearch(mockWriter, StackType.CUSTOM_STACK, UniqueSetType.PRE_MADE_HASH_SET),
                    new DepthSearch(mockWriter, StackType.CUSTOM_STACK, UniqueSetType.CUSTOM_SET_SIZE_HASH_SET)
            ).map(Arguments::of);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DepthSearchArgumentsProvider.class)
    public void doesNotThrowErrorsWithValidMapTest(DepthSearch depthSearch) throws IOException {
        depthSearch.setMapClean(WebMapMock.getMinimumValidMap());
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Depth first", mockWriter.getReceivedPath());
    }

    @ParameterizedTest
    @ArgumentsSource(DepthSearchArgumentsProvider.class)
    public void doesThrowErrorsWithInvalidMapTest(DepthSearch depthSearch) {
        var invalidMap = WebMapMock.getMinimumValidMap();
        invalidMap.setTileTarget(-1, 22);
        depthSearch.setMapClean(invalidMap);

        Throwable exception = assertThrows(IllegalStateException.class, () -> depthSearch.runSearch());
        assertEquals("Requires valid map, and name for algorithm", exception.getMessage());
    }

    /**
     * tests all but hardware and jvm related untestable values
     *
     * @throws IOException shouldnt happen because writer is mocked
     */
    @ParameterizedTest
    @ArgumentsSource(DepthSearchArgumentsProvider.class)
    public void valuesSetCorectlyWhereStartIsTargetTest(DepthSearch depthSearch) throws IOException {
        WebMap map = WebMapMock.getMinimumValidMap();
        map.setTileStart(map.getTileTarget().x, map.getTileTarget().y);
        depthSearch.setMapClean(map);
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Depth first", mockWriter.getReceivedPath());
        assertEquals("Depth first with " + depthSearch.getUniqueSetType() + " to keep track of visited edges and " + depthSearch.getStackType() + " as an implementation of stack.", mockWriter.receivedAlDoc());
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

    @ParameterizedTest
    @ArgumentsSource(DepthSearchArgumentsProvider.class)
    public void valuesSetCorectlyWhereStartIsNotTargetTest(DepthSearch depthSearch) throws IOException {
        depthSearch.setMapClean(WebMapMock.getValid6x7Map());
        depthSearch.runSearch();
        assertTrue(mockWriter.isValidatingReturnedTrue());
        assertEquals("/doc/reports/nameless map/Depth first", mockWriter.getReceivedPath());
        assertEquals("Depth first with " + depthSearch.getUniqueSetType() + " to keep track of visited edges and " + depthSearch.getStackType() + " as an implementation of stack.", mockWriter.receivedAlDoc());
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

