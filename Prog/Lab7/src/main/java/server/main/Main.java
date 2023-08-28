package server.main;

import ch.qos.logback.classic.Logger;
import common.models.Color;
import common.models.Coordinates;
import org.slf4j.LoggerFactory;
import server.auth.AuthManager;
import server.database.ConnectionFactory;
import server.database.DatabaseManager;
import server.handlers.LabWorkHandler;
import common.models.LabWork;
import common.utils.ModelTree;
import server.network.Server;
import server.network.TcpServer;
import server.сommands.CommandManager;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Program entry point
 * Program managing collection of LabWork type
 */
public class Main {
    public static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger("server");

    public static void main(String[] args) {
        Server.setPort(args);
        ConnectionFactory.init(args[0]);
        ModelTree tree = new ModelTree(LabWork.class);
        LabWorkHandler handler = new LabWorkHandler(tree);
        AuthManager authManager = new AuthManager();
        CommandManager commandManager = new CommandManager(handler, authManager);
        Server server = new TcpServer(commandManager, authManager);
        server.run();
    }
}
