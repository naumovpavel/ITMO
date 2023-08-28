package server.сommands;

import server.handlers.CollectionHandler;
import org.json.JSONException;
import server.utils.input.BufferedReader;
import server.utils.ModelTree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

/**
 * Execute script command
 */
public class ExecuteScript extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;
    private CommandManager commandManager;
    private static Stack<String> callStack = new Stack<>();

    public ExecuteScript(CollectionHandler<?, ?> handler) {
        super("execute_script fileName", "Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.handler = handler;
        this.tree = handler.getTree();
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2) {
            throw new IllegalArgumentException("Не введено имя файла");
        }

        BufferedReader in = null;

        try {
            if(checkForLoop(args[1])) {
               throw new IllegalArgumentException("Невозможно исполнить скрипт, так как он приводит к зацикливанию");
            }
            in = new BufferedReader(new FileInputStream(args[1]));
            callStack.push(args[1]);
            CommandManager commandManager = new CommandManager(in, handler, ManagerMode.NoUser);
            commandManager.startExecuting();
            callStack.pop();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Не удалось открыть файл");
        } catch (JSONException e) {
            throw new IllegalArgumentException("Файл заполнен не правильно");
        } catch (IOException e) {
            throw new IllegalArgumentException("Не прочитать файл");
        }
        System.out.println("Скрипт успешно исполнен");
    }

    private boolean checkForLoop(String fileName) {
        return callStack.contains(fileName);
    }
}
