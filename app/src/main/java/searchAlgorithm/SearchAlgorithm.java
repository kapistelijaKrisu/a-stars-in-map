package searchAlgorithm;

import model.WebMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SearchAlgorithm {
    protected WebMap map;
    private String reportFilePath;

    protected abstract void searchAlgorithm();

    //todo write to a file, add filepath to constructor
    // todo announce completion details...where results written etc
    public void runSearch() {
        if (reportFilePath != null) { // if processed already
            prepareReportDirectoriesAndFile();
        }
        searchAlgorithm();


    }

    //todo test path forms in jar
    private void prepareReportDirectoriesAndFile() {
        var root = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParent();
        //create sub directories
        //get report directorypath
        //generate filename
        //doc/reports/algoritmin_nimi/kartan_nimi/aika_leima.md
        reportFilePath = "asd.md";

    }

    protected FileWriter getWriterForUse() throws IOException {
        return new FileWriter(reportFilePath);
    }


    public void setMap(WebMap map) {
        this.map = map;
    }
}
