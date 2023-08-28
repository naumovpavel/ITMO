package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Head command
 */
public class Head extends Command {

    public Head() {
        super("head", "Выводит первый элемент в коллекции");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.HEAD);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.get("model").toString());
    }
}
