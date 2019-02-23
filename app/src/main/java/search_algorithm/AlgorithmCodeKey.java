package search_algorithm;

import java.util.HashMap;
import java.util.Map;

public enum AlgorithmCodeKey {
    WIDTH_FIRST_WITH_CUSTOM_STACK_AND_PRE_MADE_HASH_SET("bcp"),
    WIDTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_SET_SIZE_HASH_SET("bcs"),
    WIDTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET("bcd"),
    WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_PRE_MADE_HASH_SET("bsp"),
    WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_SET_SIZE_HASH_SET("bss"),
    WIDTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET("bsd"),

    DEPTH_FIRST_WITH_CUSTOM_STACK_AND_PRE_MADE_HASH_SET("scp"),
    DEPTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_SET_SIZE_HASH_SET("scs"),
    DEPTH_FIRST_WITH_CUSTOM_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET("scd"),
    DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_PRE_MADE_HASH_SET("ssp"),
    DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_SET_SIZE_HASH_SET("sss"),
    DEPTH_FIRST_WITH_PRE_MADE_STACK_AND_CUSTOM_DYNAMIC_SIZE_HASH_SET("ssd"),

    DIJKSTRA_WITH_PRE_MADE_HEAP_AND_2D_DISTANCES_ARRAY("dh2"),
    DIJKSTRA_WITH_PRE_MADE_HEAP_AND_1D_DISTANCES_ARRAY("dh1"),
    DIJKSTRA_WITH_CUSTOM_HEAP_AND_2D_DISTANCES_ARRAY("dc2"),
    DIJKSTRA_WITH_CUSTOM_HEAP_AND_1D_DISTANCES_ARRAY("dc1"),

    A_STAR_WITH_PRE_MADE_HEAP_AND_2D_DISTANCES_ARRAY("ah2"),
    A_STAR_WITH_PRE_MADE_HEAP_AND_1D_DISTANCES_ARRAY("ah1"),
    A_STAR_WITH_CUSTOM_HEAP_AND_2D_DISTANCES_ARRAY("ac2"),
    A_STAR_WITH_CUSTOM_HEAP_AND_1D_DISTANCES_ARRAY("ac1");

    private final String stringValue;

    AlgorithmCodeKey(String nodeType) {
        this.stringValue = nodeType;
    }

    public String getStringValue() {
        return stringValue;
    }

    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<String, AlgorithmCodeKey> lookup = new HashMap<>();

    static {
        for (AlgorithmCodeKey d : AlgorithmCodeKey.values()) {
            lookup.put(d.getStringValue(), d);
        }
    }
    public static AlgorithmCodeKey get(String abbreviation) {
        return lookup.get(abbreviation);
    }
}