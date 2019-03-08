package system_tools;

/**
 * Replaces all line breaks into OS style line breaks
 */
public class LineBreaker {

    /**
     * Recreates the string replacing line breaks with System.lineSeparator()
     * Does nothing if no Line breaks are in param
     *
     * @param string recreation made of. Null safe.
     * @return string where replaced all line breaks into System.lineSeparator().
     */
    public static String formatBreaks(String string) {
        if (string == null) return null;
        return string.replaceAll("\\n|\\r\\n", System.lineSeparator());
    }
}
