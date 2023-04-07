package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

import java.util.Collection;

/**
 * Print field descending tuned in works  command
 */
public class PrintFieldDescendingTunedInWorks extends Command {

    public PrintFieldDescendingTunedInWorks() {
        super("print_field_descending_tuned_in_works", "Выводит значения поля tunedInWorks всех элементов в порядке убывания");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.PRINT_FIELD_DESCENDING_TUNED_IN_WORKS);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        Collection<Integer> array = response.get("array");

        for(var x : array) {
            System.out.println(x);
        }
    }
}
