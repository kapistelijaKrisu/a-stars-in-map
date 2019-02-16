package app;

import IOoperations.analysisWriter.AnalysisWriter;

import java.util.Scanner;

public class Main {

    /**
     * Entry point for program
     * 1st argument is devmode, true if argument is true else false.
     * @param args entrypoint arguments.
     */
    public static void main(String[] args) {

        AnalysisWriter analysisWriter = new AnalysisWriter();
        boolean devMode = args.length == 0 || args[0].equals("true");
        var app = new App(devMode, new Scanner(System.in), analysisWriter);
        app.run();
    }
}
