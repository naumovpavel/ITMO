package client.сommands;

import common.utils.ModelTree;
import client.utils.input.BufferedReader;
import server.handlers.CollectionHandler;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

/**
 * Execute script command
 */
public class ExecuteScript extends Command {
    private final ModelTree tree;
    private CommandManager commandManager;
    private static Stack<String> callStack = new Stack<>();

    public ExecuteScript(ModelTree tree) {
        super("execute_script fileName", "Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.tree = tree;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2) {
            throw new IllegalArgumentException("Не введено имя файла");
        }

        BufferedReader in = null;
        String fileName = null;
        try {
            fileName = Paths.get(args[1]).toRealPath().toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Файл не найден");
        }
        try {
            if(checkForLoop(fileName)) {
               throw new IllegalArgumentException("Невозможно исполнить скрипт, так как он приводит к зацикливанию");
            }
            in = new BufferedReader(new FileInputStream(args[1]));
            callStack.push(fileName);
            CommandManager commandManager = new CommandManager(in, tree, ManagerMode.NoUser);
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
