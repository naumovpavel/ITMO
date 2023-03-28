package server.сommands;

import common.request.Request;
import common.response.Response;
import server.handlers.CollectionHandler;
import server.сommands.Command;

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
        return new Response(false, "Возникли ошибки при сохранение");
    }
}
