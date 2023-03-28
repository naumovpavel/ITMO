package client.сommands;

import common.Commands;
import common.request.HeadRequest;
import common.request.InfoRequest;
import common.request.Request;
import common.response.InfoResponse;
import server.handlers.CollectionHandler;

/**
 * Info command
 */
public class Info extends Command {

    public Info() {
        super("info", "Выводит информацию о коллекции");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        InfoRequest request = new InfoRequest();
        InfoResponse response = handleResponse(request);

        if(response != null) {
            System.out.println(response.getAnswer());
        }
    }
}
