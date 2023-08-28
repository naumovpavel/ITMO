package client.сommands;

import client.gui.commandsView.ExecuteScriptView;
import client.utils.input.BuilderStrategy;
import client.utils.input.Reader;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;
import client.utils.input.BufferedReader;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONException;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Stack;

/**
 * Execute script command
 */
public class ExecuteScript extends Command {
    private final ModelTree tree;
    private CommandManager commandManager;
    private static Stack<String> callStack = new Stack<>();
    private static Stack<Pair<Reader, ManagerMode>> readersCallStack = new Stack<>();
    private BuilderStrategy builder;

    public ExecuteScript(ModelTree tree, CommandManager commandManager, BuilderStrategy builder) {
        super("execute_script", " fileName Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.tree = tree;
        this.builder = builder;
        this.commandManager = commandManager;
        view = new ExecuteScriptView(this);
        visible = true;
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
            SwingUtilities.invokeLater(() -> view.renderResponse(new Response("Файл не найден", Status.ERROR)));
            return;
        }
        try {
            if(checkForLoop(fileName)) {
                SwingUtilities.invokeLater(() -> view.renderResponse(new Response("Невозможно исполнить скрипт, так как он приводит к зацикливанию", Status.ERROR)));
                return;
            }
            in = new BufferedReader(new FileInputStream(args[1]));
            readersCallStack.push(new MutablePair<>(commandManager.getReader(), commandManager.getMode()));
            callStack.push(fileName);
            System.out.println(fileName + "fff");
            builder.setReader(in, ManagerMode.NoUser);
            commandManager.setReader(in);
            System.out.println("changed");
            //CommandManager commandManager = new CommandManager(in, tree, ManagerMode.NoUser);
            commandManager.startExecuting();
            callStack.pop();
            Pair<Reader, ManagerMode> vector = readersCallStack.pop();
            builder.setReader(vector.getLeft(), vector.getRight());
            commandManager.setReader(vector.getLeft());
        } catch (FileNotFoundException e) {
            SwingUtilities.invokeLater(() -> view.renderResponse(new Response("Не удалось открыть файл", Status.ERROR)));
            return;
        } catch (JSONException e) {
            SwingUtilities.invokeLater(() -> view.renderResponse(new Response("Файл заполнен не правильно", Status.ERROR)));
            return;
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> view.renderResponse(new Response("Не прочитать файл", Status.ERROR)));
        }
        SwingUtilities.invokeLater(() -> view.renderResponse(new Response("Скрипт успешно исполнен", Status.OK)));
    }

    private boolean checkForLoop(String fileName) {
        return callStack.contains(fileName);
    }
}
