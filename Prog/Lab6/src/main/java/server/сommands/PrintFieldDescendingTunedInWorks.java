package server.сommands;

import common.request.Request;
import common.response.PrintFieldDescendingTunedInWorksResponse;
import common.response.Response;
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
            return new PrintFieldDescendingTunedInWorksResponse(false, "Коллекция пуста");
        } else {
            return new PrintFieldDescendingTunedInWorksResponse("", true, handler.printFieldDescending());
        }
    }
}
