package system_tools;

/**
 * Checks if file name is legal is various operating systems
 */
public class LegalFileName {

    /**
     * Characters known for being illegal in a filename
     */
    public static final char[] ILLEGAL_CHARACTERS = {'/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

    /**
     * @param name to be validated
     * @return true if none of ILLEGAL_CHARACTERS is found in name and it's not null or empty
     */
    public static boolean isValidFileName(String name) {
        if (name == null || name.isBlank()) return false;
        for (char illegalCharacter : ILLEGAL_CHARACTERS) {
            for (char charAt : name.toCharArray()) {
                if (charAt == illegalCharacter) return false;
            }
        }
        return true;
    }

    /**
     * @param names to be validated
     * @return true if none of ILLEGAL_CHARACTERS is found in names and it's not null or empty
     */
    public static boolean areValidFileNames(String[] names) {
        if (names == null || names.length == 0) return false;
        for (String name : names) {
            if (!isValidFileName(name)) return false;
        }
        return true;
    }
}
