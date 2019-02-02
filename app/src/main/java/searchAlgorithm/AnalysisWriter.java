package searchAlgorithm;

import systemTools.DateConverter;
import systemTools.SystemSpecReader;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * work in progress
 * todo mention where / is put
 */
public class AnalysisWriter {

    private static final SystemSpecReader systemSpecReader = new SystemSpecReader();
    private static final DateConverter dateConverter = new DateConverter();
    private String root;

    public AnalysisWriter() {
        root = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParent();
    }


    //todo replace pre-calculated values, system specs, theoretical algorithm stats
    public void writeReport(Map<String, String> replacingValues, String path) throws IOException {
        buildDirectories(path);
        String template = readRawReportTemplate();
        //process
        PrintWriter writer = getAnalysisWriter(path);
        //write
        writer.close();
    }

    /**
     *
     * @return ReportTemplate.md from resources as String
     */

    private String readRawReportTemplate() {
        InputStream in = getClass().getResourceAsStream("/ReportTemplate.md");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     *
     * @param reportFilePath path where analysis file will be
     * @return PrintWriter that writes over.
     * @throws IOException if no such path exists
     */

    private PrintWriter getAnalysisWriter(String reportFilePath) throws IOException {
        if (reportFilePath == null) throw new IOException("No file path given");
        var reportFileName = AnalysisWriter.dateConverter.getDateAsString(LocalDateTime.now()) + ".md";
        return new PrintWriter(new BufferedWriter(new FileWriter(root + "/" + reportFilePath + "/" + reportFileName, false)));
    }

    /**
     * builds directories according to given path
     * todo set root from cmd args
     * @param reportFilePath given path
     */
    private void buildDirectories(String reportFilePath) {
        File reportDirectory = new File(root + "/" + reportFilePath);
        reportDirectory.mkdirs();
    }
}
