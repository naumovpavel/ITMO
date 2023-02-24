package main;

import handlers.LabWorkHandler;
import models.LabWork;
import utils.ModelTree;
import сommands.CommandManager;


public class Main {
    public static void main(String[] args) {
        System.out.println("прив! используй help, чтобы посмотреть список команд");
        ModelTree tree = new ModelTree(LabWork.class);
        LabWorkHandler handler = new LabWorkHandler(tree);
        CommandManager commandManager = new CommandManager(handler);
        commandManager.startExecuting();
    }
}
