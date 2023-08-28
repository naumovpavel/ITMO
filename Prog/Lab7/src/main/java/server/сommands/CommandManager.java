package server.сommands;

import common.Commands;
import common.models.User;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.auth.AuthManager;
import server.handlers.CollectionHandler;

import java.io.IOException;
import java.util.*;

/**
 * Class that manages commands
 */
public class CommandManager {
    private final HashMap<String, Command> commands;
    private CollectionHandler<?, ?> handler;

    /**
     * Command manger constructor
     * @param handler collection handler
     * @see CollectionHandler
     */
    public CommandManager(CollectionHandler<?, ?> handler, AuthManager authManager) {
        this.commands = new HashMap<>();
        this.commands.put("add", new Add(handler));
        this.commands.put("show", new Show(handler));
        this.commands.put("exit", new Exit(handler));
        this.commands.put("clear", new Clear(handler));
        this.commands.put("get_by_id", new GetById(handler));
        this.commands.put("update", new Update(handler));
        this.commands.put("count_less_than_author", new CountLessThanAuthor(handler));
        this.commands.put("head", new Head(handler));
        this.commands.put("info", new Info(handler));
        this.commands.put("remove_by_id", new RemoveById(handler));
        this.commands.put("remove_head", new RemoveHead(handler));
        this.commands.put("remove_lower", new RemoveLower(handler));
        this.commands.put("max_by_name", new MaxByName(handler));
        this.commands.put("login", new Login(authManager));
        this.commands.put("register", new Register(authManager));
        this.commands.put("print_field_descending_tuned_in_works", new PrintFieldDescendingTunedInWorks(handler));
    }

    public void startLocalExecuting() {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (true) {
            try {
                if(System.in.available() == 0) {
                    break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            command = scanner.nextLine();
            command = command.strip();
            try {
                Request request = new Request(Commands.valueOf(command.toUpperCase()), new User());
                Response response = executeCommand(request);
                if(response.getStatus() == Status.OK) {
                    System.out.println(response.getAnswer());
                } else {
                    System.out.println(response.getError());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Вы ввели не существующую команду");
            }
        }
    }

    /**
     * Method that executes command
     */
    public Response executeCommand(Request request)  {
        if(!commands.containsKey(request.getType().getName())) {
            throw new IllegalArgumentException("Данной команды не существует");
        }
        return commands.get(request.getType().getName()).execute(request);
    }
}
