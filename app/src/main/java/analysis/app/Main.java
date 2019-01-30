package main.java.analysis.app;

import main.java.analysis.app.App;

public class Main {

    /*add path where to save test results later as param, add dev mode option  */
    public static void main(String[] args) {
        System.out.println("Hello there");
        var app = new App(true);
        app.run();
    }
}
