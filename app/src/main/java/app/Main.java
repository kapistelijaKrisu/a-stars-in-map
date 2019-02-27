package app;

import file_operations.analysis_writer.AnalysisWriter;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * Entry point for program
     * @param args needed argument by main method. Not used.
     */
    public static void main(String[] args) {

        AnalysisWriter analysisWriter = new AnalysisWriter();
        var app = new App(new Scanner(System.in), analysisWriter);
        app.run();
    }
}
