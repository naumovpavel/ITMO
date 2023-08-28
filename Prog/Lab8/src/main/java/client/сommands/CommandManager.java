package client.сommands;

import client.gui.commandsView.CommandView;
import client.handlers.CollectionPublisher;
import client.network.Client;
import client.network.TcpClient;
import client.utils.input.*;
import common.models.User;
import common.utils.ModelTree;
import org.apache.commons.lang3.ArrayUtils;
import server.handlers.CollectionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class that manages commands
 */
public class CommandManager {
    private final HashMap<String, Command> commands;
    private static final Executor executionThread = Executors.newSingleThreadExecutor();
    private Queue<Command> history;
    private ManagerMode mode;
    private Reader reader;
    private ModelTree tree;
    private final Client client;
    private CollectionPublisher publisher;
    private BuilderStrategy builder = new BuilderStrategy();
    private long TIMEOUT_PERIOD = 10000;

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
        this.publisher = new client.handlers.CollectionHandler(this);
        this.reader = reader;
        this.tree = tree;
        this.client = TcpClient.getInstance();

        //setReader(reader, mode);
        CommandView.setCommandManager(this);
        builder.setReader(reader, mode);


        this.commands.put("show", new Show(tree, publisher));
        this.commands.put("logout", new Logout());
        this.commands.put("clear", new Clear());
        this.commands.put("info", new Info());
        this.commands.put("filter", new Filter(tree));
        this.commands.put("sort", new Sort(tree));
        this.commands.put("max_by_name", new MaxByName(publisher));
        this.commands.put("get_events", new GetEvents(publisher));
        this.commands.put("head", new Head(publisher));
        this.commands.put("remove_head", new RemoveHead(publisher));
        this.commands.put("remove_lower", new RemoveLower(tree, builder));
        this.commands.put("add", new Add(tree, builder));
        this.commands.put("count_less_than_author", new CountLessThanAuthor(builder));
        this.commands.put("change", new Change(tree, builder));
        this.commands.put("update", new Update(tree, builder));
        this.commands.put("login", new Login(new ModelTree(User.class), builder));
        this.commands.put("register", new Register(new ModelTree(User.class), builder));
        this.commands.put("execute_script", new ExecuteScript(tree, this, builder));
        this.commands.put("get_by_id", new GetById());
        this.commands.put("remove_by_id", new RemoveById());
    }

    /**
     * Default constructor
     * @see CollectionHandler
     */
    public CommandManager(ModelTree tree) {
        this(new CliReader(), tree, ManagerMode.User);
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
    public ManagerMode getMode() {
        return mode;
    }

    public CollectionPublisher getPublisher() {
        return publisher;
    }

    public Reader getReader() {
        return reader;
    }

    /**
     * Method that scans and executes commands
     */
    public void startExecuting() throws IllegalArgumentException {
        while (reader.hasNextLine()) {
            String command = reader.nextLine();

            if(command.isEmpty() || command.isBlank()) {
                continue;
            }

            executeCommand(command.split(" "));
        }
    }

    /**
     * Method that executes command
     * @param args commands arguments
     */
    public void executeCommand(String[] args)  {
        if (!commands.containsKey(args[0])) {
            System.out.println(("Данной команды не существует"));
            return;
        }
        commands.get(args[0]).execute(args);
        addToHistory(commands.get(args[0]));
    }

    public void executeCommand(String command, String... args)  {
        CommandManager.executionThread.execute(() -> {
            if(!commands.containsKey(command)) {
                System.out.println(("Данной команды не существует"));
                return;
            }
            commands.get(command).execute(ArrayUtils.add(args, 0, command));
        });
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

    public BuilderStrategy getBuilder() {
        return builder;
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
