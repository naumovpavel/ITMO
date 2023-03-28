package client.сommands;

import common.Commands;
import common.request.HeadRequest;
import common.request.PrintFieldDescendingTunedInWorksRequest;
import common.request.Request;
import common.response.HeadResponse;
import common.response.PrintFieldDescendingTunedInWorksResponse;
import server.handlers.CollectionHandler;

/**
 * Print field descending tuned in works  command
 */
public class PrintFieldDescendingTunedInWorks extends Command {

    public PrintFieldDescendingTunedInWorks() {
        super("print_field_descending_tuned_in_works", "Выводит значения поля tunedInWorks всех элементов в порядке убывания");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        PrintFieldDescendingTunedInWorksRequest request = new PrintFieldDescendingTunedInWorksRequest();
        PrintFieldDescendingTunedInWorksResponse response =  handleResponse(request);

        if(response == null) {
            return;
        }
        for(var x : response.getArray()) {
            System.out.println(x);
        }
    }
}
