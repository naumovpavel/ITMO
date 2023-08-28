package server.utils.input;

import java.util.Scanner;

/**
 * Class that implements interface Reader using Scanner
 * @see Reader
 */
public class CliReader implements Reader {
    private Scanner scanner;

    /**
     * Default constructor
     */
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
