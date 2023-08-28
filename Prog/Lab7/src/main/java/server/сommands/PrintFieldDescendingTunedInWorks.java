package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

/**
 * Print field descending tuned in works  command
 */
public class PrintFieldDescendingTunedInWorks extends Command {
    private final CollectionHandler<?, ?> handler;

    public PrintFieldDescendingTunedInWorks(CollectionHandler<?, ?> handler) {
        super("print_field_descending_tuned_in_works", "Выводит значения поля tunedInWorks всех элементов в порядке убывания");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        if(handler.getCollection().size() == 0) {
            return new Response("Коллекция пуста", Status.OK);
        } else {
            return new Response("", Status.OK).put("array", handler.printFieldDescending());
        }
    }
}
