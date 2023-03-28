package server.main;

import server.handlers.LabWorkHandler;
import common.models.LabWork;
import common.utils.ModelTree;
import server.network.Server;
import server.network.TcpServer;
import server.сommands.CommandManager;

import java.util.Objects;

/**
 * Program entry point
 * Program managing collection of LabWork type
 */
public class Main {
    public static void main(String[] args) {
        Server.setPort(args);
        ModelTree tree = new ModelTree(LabWork.class);
        LabWorkHandler handler = new LabWorkHandler(tree);
        CommandManager commandManager = new CommandManager(handler);
        Server server = new TcpServer(commandManager);
        server.run();
    }
}
