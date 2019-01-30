package app;

import java.util.Scanner;

public class Main {

    /*add path where to save test results later as param, add dev mode option  */
    public static void main(String[] args) {
        System.out.println("Hello there");
        var app = new App(true, new Scanner(System.in));
        app.run();
    }
}
