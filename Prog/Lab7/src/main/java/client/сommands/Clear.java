package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Clear command
 */
public class Clear extends Command {

    public Clear() {
        super("clear", "Очищает коллекцию");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.CLEAR);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.getAnswer());
    }
}
