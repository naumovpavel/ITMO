package client.main;

import client.gui.frames.AuthWindow;
import client.gui.frames.MainWindow;
import client.gui.ui.ModelsMap;
import client.network.Client;
import client.utils.input.ArrayReader;
import client.utils.input.BufferedReader;
import client.сommands.ManagerMode;
import common.utils.ModelTree;
import common.models.LabWork;
import client.сommands.CommandManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Program entry point
 * Program managing collection of LabWork type
 */



public class Main {

    public static Locale locale = Locale.getDefault();

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("не задан конфиг");
            System.exit(1);
        }

        ModelsMap.setCfg(args[0]);
        Client.setPort(args);
        Client.setHost(args);
        ModelTree tree = new ModelTree(LabWork.class);
        CommandManager commandManager = new CommandManager(new ArrayReader(), tree, ManagerMode.GUI);

        MainWindow mainWindow = new MainWindow(commandManager, tree);
        AuthWindow authWindow = new AuthWindow(commandManager);

        EventQueue.invokeLater(() -> {
            authWindow.render();
            authWindow.setTitle("Authentication");
            authWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            authWindow.setVisible(true);
        });

        //commandManager.startExecuting();
    }
}
