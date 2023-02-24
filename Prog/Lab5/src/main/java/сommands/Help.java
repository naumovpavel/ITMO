package сommands;

import java.util.HashMap;

public class Help extends Command{
    private final HashMap<String, Command> commands;

    public Help(HashMap<String, Command> commands) {
        super("help", "Выводит список команд");
        this.commands = commands;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        for(var command : commands.values()) {
            System.out.println(command.getName() + " " + command.getDescription());
        }
    }
}
