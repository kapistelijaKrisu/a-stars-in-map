package system_tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LegalFileNameTest {
    @Test
    public void stringNullTest() {
        assertFalse(LegalFileName.isValidFileName(null));
        assertFalse(LegalFileName.areValidFileNames(null));
    }
    @Test
    public void isStringValidNameTest() {
        String validFileName = " valid string G .-.";
        assertTrue(LegalFileName.isValidFileName(validFileName));
    }
    @Test
    public void areStringsValidNameTest() {
        String[] validFileNames = new String[]{" valid string G .-."};
        assertTrue(LegalFileName.areValidFileNames(validFileNames));
        validFileNames = new String[]{" valid string G .-.", " valid string G .-.", " valid string G .-."};
        assertTrue(LegalFileName.areValidFileNames(validFileNames));
    }
    @Test
    public void isStringInvalidNameTest() {
        for (char invalid: LegalFileName.ILLEGAL_CHARACTERS) {
            assertFalse(LegalFileName.isValidFileName("valid start" + invalid));
        }
        assertFalse(LegalFileName.isValidFileName(" " + System.lineSeparator()));
    }
    @Test
    public void areStringsInvalidNameTest() {
        for (char invalid: LegalFileName.ILLEGAL_CHARACTERS) {
            assertFalse(LegalFileName.areValidFileNames(new String[]{"valid start" + invalid}));
        }
        assertFalse(LegalFileName.areValidFileNames(new String[]{" " + System.lineSeparator()}));
        assertFalse(LegalFileName.areValidFileNames(new String[]{}));
    }
}
