package app;

import systemTools.SystemSpecReader;

import java.util.Scanner;

public class Main {

    /**
     * Entry point for program
     * @param args entrypoint arguments
     * Currently test results in a fixed location and dev mode is hardcoded
     * TODO add path where to save test results later as param, add dev mode option
     * */
    public static void main(String[] args) {
        System.out.println("Hello there");
        var app = new App(true, new Scanner(System.in));
        app.run();
    }
}
