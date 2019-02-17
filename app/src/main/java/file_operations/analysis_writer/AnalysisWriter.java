package file_operations.analysis_writer;

import system_tools.DateConverter;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A file writer that uses ReportTemplate.md to write an analysis at root level.
 */
public class AnalysisWriter {

    private static final DateConverter dateConverter = new DateConverter();
    private static final ReportValidator reportValidator = new ReportValidator();
    private String root;

    /**
     * A file writer that uses ReportTemplate.md to write an analysis of search algorithm at root level.
     */
    public AnalysisWriter() {
        root = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParent();
    }

    /**
     * Reads ReportTemplate.md and replaces keys according to replacingValues. Then writes it to a timestamp by DateConverter .md file at given path directory.
     *
     * @param replacingValues Template keys to be replaced with these values
     * @param path            in what directory structure will the report be having app level as root. Will create folders if  missing.
     * @throws IOException              when writing doesn't succeed
     * @throws IllegalArgumentException if path is null or replacingValues is not valid according to ReportValidator class.
     */
    public void writeReport(Map<String, String> replacingValues, String path) throws IOException, IllegalArgumentException {
        if (!AnalysisWriter.reportValidator.validateMapper((replacingValues)) || path == null)
            throw new IllegalArgumentException(AnalysisWriter.reportValidator.getValidatorCondition());
        buildDirectories(path);
        String template = readRawReportTemplate();
        System.out.println("Beginning to parse template");
        for (Map.Entry<String, String> pair : replacingValues.entrySet()) {
            template = template.replace(pair.getKey(), pair.getValue());
        }
        System.out.println("Parsing done. Proceeding to write into a file");

        PrintWriter writer = getAnalysisWriter(path);
        writer.write(template);
        writer.close();
        System.out.println("Successfully wrote report");

    }

    private String readRawReportTemplate() {
        InputStream in = getClass().getResourceAsStream("/ReportTemplate.md");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private PrintWriter getAnalysisWriter(String reportFilePath) throws IOException {
        if (reportFilePath == null) throw new IOException("No file path given");
        var reportFileName = AnalysisWriter.dateConverter.getDateAsString(LocalDateTime.now()) + ".md";
        return new PrintWriter(new BufferedWriter(new FileWriter(root + "/" + reportFilePath + "/" + reportFileName, false)));
    }

    private void buildDirectories(String reportFilePath) {
        File reportDirectory = new File(root + "/" + reportFilePath);
        reportDirectory.mkdirs();
    }
}
