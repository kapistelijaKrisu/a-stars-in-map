package system_tools;

public class SystemLine {
    public static String breakLine(String string) {
        return string.replaceAll("\\n|\\r\\n", System.lineSeparator());
    }
}
