package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Info command
 */
public class Info extends Command {

    public Info() {
        super("info", "Выводит информацию о коллекции");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {

        Request request = new Request(Commands.INFO);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.getAnswer());

    }
}
