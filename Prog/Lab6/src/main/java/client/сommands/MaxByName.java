package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Max by name command
 */
public class MaxByName extends Command {

    public MaxByName() {
        super("max_by_name", "Выводит любой объект из коллекции, значение поля name которого является максимальным");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.MAX_BY_NAME);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.get("model").toString());
    }
}
