package client.сommands;

import common.Commands;
import common.request.ClearRequest;
import common.request.Request;
import common.response.ClearResponse;
import common.response.Response;

import java.io.IOException;

/**
 * Clear command
 */
public class Clear extends Command {

    public Clear() {
        super("clear", "Очищает коллекцию");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        ClearRequest request = new ClearRequest();
        ClearResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getAnswer());
        }
    }
}
