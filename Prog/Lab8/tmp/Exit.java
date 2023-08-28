package server.сommands;

/**
 * Exit command
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "Выход из программы");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        System.out.println("adiós amigo");
        System.exit(0);
    }
}
