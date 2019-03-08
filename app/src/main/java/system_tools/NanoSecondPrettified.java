package system_tools;

/**
 * Converts nano seconds to more readable form
 */
public class NanoSecondPrettified {

    /**
     * splits nanos to sec ms ns
     * if sec = 0 then skips sec
     * if ms = 0 then skips ms
     * @param time time in nanos
     * @return time in nanos in more readable form sec ms ns
     */
    public static String prettifyNanoSeconds(long time) {
        long nanos = time % 1000000;
        long milliseconds = (time / 1000000) % 1000;
        long seconds = (time / 1000000000);
        String timeReport = "";
        if (seconds > 0) timeReport += seconds + "sec ";
        if (milliseconds > 0) timeReport += milliseconds + "ms ";
        timeReport += nanos + "ns";
        return timeReport;
    }
}
