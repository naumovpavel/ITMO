package client.сommands;

import common.Commands;
import common.request.HeadRequest;
import common.request.RemoveHeadRequest;
import common.request.Request;
import common.response.HeadResponse;
import common.response.RemoveHeadResponse;
import server.handlers.CollectionHandler;

/**
 * Remove head command
 */
public class RemoveHead extends Command {

    public RemoveHead() {
        super("remove_head", "Удаляет первый элемент коллекции");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        RemoveHeadRequest request = new RemoveHeadRequest();
        RemoveHeadResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getModel());
        }
    }
}
