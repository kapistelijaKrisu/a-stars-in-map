package app;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;


public class AppTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);

    private Scanner scanner;

    private App app;

    @Test
    public void exitsCorrectly() {
        String input = "not a command";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        scanner =  new Scanner(System.in);
        app = new App(true, scanner);
        app.run();
    }
}
