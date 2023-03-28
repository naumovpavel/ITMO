package client.сommands;

import common.Commands;
import common.request.HeadRequest;
import common.request.MaxByNameRequest;
import common.request.Request;
import common.response.HeadResponse;
import common.response.MaxByNameResponse;
import server.handlers.CollectionHandler;

/**
 * Max by name command
 */
public class MaxByName extends Command {

    public MaxByName() {
        super("max_by_name", "Выводит любой объект из коллекции, значение поля name которого является максимальным");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        MaxByNameRequest request = new MaxByNameRequest();
        MaxByNameResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getModel());
        }
    }
}
