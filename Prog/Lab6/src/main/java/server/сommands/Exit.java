package server.сommands;

import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.handlers.CollectionHandler;

/**
 * Exit command
 */
public class Exit extends Command {
    private final CollectionHandler<?, ?> handler;

    public Exit(CollectionHandler<?, ?> handler) {
        super("exit", "Выход из программы");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) throws IllegalArgumentException {
        if(handler.save()) {
            System.out.println("adiós amigo");
            System.exit(0);
        }
        return new Response("Возникли ошибки при сохранение", Status.ERROR);
    }
}
