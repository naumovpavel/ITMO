package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Remove head command
 */
public class RemoveHead extends Command {

    public RemoveHead() {
        super("remove_head", "Удаляет первый элемент коллекции");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.REMOVE_HEAD);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.get("model").toString());
    }
}
