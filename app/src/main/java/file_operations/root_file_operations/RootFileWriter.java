package file_operations.root_file_operations;

import system_tools.DateConverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Writer that does the actual writing on file having RootFolderFinder.getRootFolder as starting point for path.
 */
public class RootFileWriter {
    /**
     * Writes content of file on a file which full name will be fileName +  DateConverter.getDateAsString(LocalDateTime.now()) of + fileType.
     * It will be written on path resulting from RootFolderFinder.getRootFolder + reportFilePath
     *
     * @param content        content to write on file
     * @param reportFilePath where to write having RootFolderFinder.getRootFolder as starting point for path.
     * @param fileName       prefix of file name
     * @param fileType       suffix of file name
     * @throws IOException if params are invalid, null or leading to non existant content or directory path
     */
    public static void writeMdFileWithTime(String content, String reportFilePath, String fileName, String fileType) throws IOException {
        if (fileName == null) throw new IOException("No file path given");
        if (fileType == null) throw new IOException("No file type given");
        if (content == null) throw new IOException("No content given to write");

        var resultingFileName = fileName + " " + DateConverter.getDateAsString(LocalDateTime.now()) + fileType;
        var root = RootFolderFinder.getRootFolder();
        PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(
                                root + (reportFilePath == null ? "/" : ("/" + reportFilePath + "/")) + resultingFileName, false)));
        writer.write(content);
        writer.close();
    }

}
