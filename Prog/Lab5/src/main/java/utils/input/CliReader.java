package utils.input;

import java.util.Scanner;

public class CliReader implements Reader {
    private Scanner scanner;

    public CliReader() {
        scanner = new Scanner(System.in);
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
