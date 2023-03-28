package client.сommands;

import common.Commands;
import common.request.GetByIdRequest;
import common.request.HeadRequest;
import common.request.Request;
import common.response.GerByIdResponse;
import common.response.HeadResponse;
import common.utils.Converter;
import server.handlers.CollectionHandler;

/**
 * Head command
 */
public class Head extends Command {

    public Head() {
        super("head", "Выводит первый элемент в коллекции");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        HeadRequest request = new HeadRequest();
        HeadResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getModel());
        }
    }
}
