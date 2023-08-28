package client.main;

import client.network.Client;
import common.utils.ModelTree;
import common.models.LabWork;
import client.сommands.CommandManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Program entry point
 * Program managing collection of LabWork type
 */
public class Main {
    public static void main(String[] args) {
        Client.setPort(args);
        Client.setHost(args);
        System.out.println("День добрый! Используйте help, чтобы посмотреть список команд");
        ModelTree tree = new ModelTree(LabWork.class);
        CommandManager commandManager = new CommandManager(tree);
        commandManager.startExecuting();
    }
}
