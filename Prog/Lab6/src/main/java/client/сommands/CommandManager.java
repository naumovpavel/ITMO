package client.сommands;

import client.network.Client;
import client.network.TcpClient;
import client.utils.input.*;
import common.utils.ModelTree;
import server.handlers.CollectionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Class that manages commands
 */
public class CommandManager {
    private final HashMap<String, Command> commands;
    private Queue<Command> history;
    private final ManagerMode mode;
    private Reader reader;
    private ModelTree tree;
    private final Client client;
    private long TIMEOUT_PERIOD = 100;

    /**
     * Command manger constructor
     * @param reader reader
     * @see Reader
     * @see CollectionHandler
     * @param mode command manager mode
     */
    public CommandManager(Reader reader, ModelTree tree, ManagerMode mode) {
        this.mode = mode;
        this.commands = new HashMap<>();
        this.history = new LinkedList<>();
        this.reader = reader;
        this.tree = tree;
        this.client = TcpClient.getInstance();


        this.commands.put("exit", new Exit());
        this.commands.put("help", new Help(commands));
        this.commands.put("show", new Show());
        this.commands.put("info", new Info());
        this.commands.put("head", new Head());
        this.commands.put("clear", new Clear());
        this.commands.put("remove_by_id", new RemoveById());
        this.commands.put("max_by_name", new MaxByName());
        this.commands.put("remove_head", new RemoveHead());
        this.commands.put("execute_script", new ExecuteScript(tree));
        this.commands.put("print_field_descending_tuned_in_works", new PrintFieldDescendingTunedInWorks());
        switch (mode) {
            case User -> {
                this.commands.put("count_less_than_author", new CountLessThanAuthor(new CLIBuilder(reader)));
                this.commands.put("remove_lower", new RemoveLower(tree, new CLIBuilder(reader)));
                this.commands.put("add", new Add(tree, new CLIBuilder(reader)));
                this.commands.put("update", new Update(tree, new CLIBuilder(reader)));
            }
            case NoUser -> {
                this.commands.put("count_less_than_author", new CountLessThanAuthor(new NoUserCLIBuilder((BufferedReader) reader)));
                this.commands.put("remove_lower", new RemoveLower(tree, new NoUserCLIBuilder((BufferedReader) reader)));
                this.commands.put("add", new Add(tree, new NoUserCLIBuilder((BufferedReader) reader)));
                this.commands.put("update", new Update(tree, new NoUserCLIBuilder((BufferedReader) reader)));
            }
        }
    }

    /**
     * Default constructor
     * @see CollectionHandler
     */
    public CommandManager(ModelTree tree) {
        this(new CliReader(), tree, ManagerMode.User);
    }

    /**
     * Method that scans and executes commands
     */
    public void startExecuting() {
        long unixTime = System.currentTimeMillis();
        long times = TIMEOUT_PERIOD;
        while (reader.hasNextLine()) {
            String command = reader.nextLine();

            if(!client.isConnected() && (System.currentTimeMillis() - unixTime) >= times) {
                try {
                    client.connect();
                } catch (IOException e) {

                }
                times *= TIMEOUT_PERIOD;
            }

            if(command.isEmpty() || command.isBlank()) {
                continue;
            }
            try {
                executeCommand(command.split(" "));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("Не удалось исполнить команду");
            }


        }
    }

    /**
     * Method that executes command
     * @param args commands arguments
     */
    public void executeCommand(String[] args)  {
        if(!commands.containsKey(args[0])) {
            throw new IllegalArgumentException("Данной команды не существует");
        }
        commands.get(args[0]).execute(args);
        addToHistory(commands.get(args[0]));
    }

    /**
     * Method that adds command to history
     * @param command command
     */
    protected void addToHistory(Command command) {
        if(history.size() > 10) {
            history.poll();
            history.add(command);
        }
    }
}
