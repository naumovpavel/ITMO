package сommands;

import handlers.CollectionHandler;
import utils.input.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CommandManager {
    private final HashMap<String, Command> commands;
    private Queue<Command> history;
    private final ManagerMode mode;
    private Reader reader;
    private CollectionHandler<?, ?> handler;

    public CommandManager(Reader reader, CollectionHandler<?, ?> handler, ManagerMode mode) {
        this.mode = mode;
        this.commands = new HashMap<>();
        this.history = new LinkedList<>();
        this.reader = reader;
        //this.handler = handler;

        this.commands.put("exit", new Exit());
        this.commands.put("help", new Help(commands));
        this.commands.put("show", new Show(handler));
        this.commands.put("info", new Info(handler));
        this.commands.put("head", new Head(handler));
        this.commands.put("clear", new Clear(handler));
        this.commands.put("remove_by_id", new RemoveById(handler));
        this.commands.put("max_by_name", new MaxByName(handler));
        this.commands.put("remove_head", new RemoveHead(handler));
        this.commands.put("save", new Save(handler));
        this.commands.put("execute_script", new ExecuteScript(handler));
        this.commands.put("print_field_descending_tuned_in_works", new PrintFieldDescendingTunedInWorks(handler));
        switch (mode) {
            case User -> {
                this.commands.put("count_less_than_author", new CountLessThanAuthor(handler, new CLIBuilder()));
                this.commands.put("remove_lower", new RemoveLower(handler, new CLIBuilder()));
                this.commands.put("add", new Add(handler, new CLIBuilder()));
                this.commands.put("update", new Update(handler, new CLIBuilder()));
            }
            case NoUser -> {
                this.commands.put("count_less_than_author", new CountLessThanAuthor(handler, new NoUserCLIBuilder((BufferedReader) reader)));
                this.commands.put("remove_lower", new RemoveLower(handler, new NoUserCLIBuilder((BufferedReader) reader)));
                this.commands.put("add", new Add(handler, new NoUserCLIBuilder((BufferedReader) reader)));
                this.commands.put("update", new Update(handler, new NoUserCLIBuilder((BufferedReader) reader)));
            }
        }
    }

    public CommandManager(CollectionHandler<?, ?> handler) {
        this(new CliReader(), handler, ManagerMode.User);
    }

    public void startExecuting() {
        while (reader.hasNextLine()) {
            String command = reader.nextLine();
            if(command.isEmpty() || command.isBlank()) {
                continue;
            }
            try {
                executeCommand(command.split(" "));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void executeCommand(String[] args)  {
        if(!commands.containsKey(args[0])) {

            throw new IllegalArgumentException("Данной команды не существует");
        }
        commands.get(args[0]).execute(args);
        addToHistory(commands.get(args[0]));
    }

    protected void addToHistory(Command command) {
        if(history.size() > 10) {
            history.poll();
            history.add(command);
        }
    }
}
