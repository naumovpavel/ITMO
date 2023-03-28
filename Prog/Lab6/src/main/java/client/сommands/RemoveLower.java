package client.сommands;

import common.Commands;
import common.request.CountLessThanAuthorRequest;
import common.request.RemoveByIdRequest;
import common.request.RemoveLowerRequest;
import common.request.Request;
import common.response.CountLessThanAuthorResponse;
import common.response.RemoveByIdResponse;
import common.response.RemoveHeadResponse;
import common.response.RemoveLowerResponse;
import common.utils.ModelTree;
import client.utils.input.Builder;
import server.handlers.CollectionHandler;

/**
 * Remove lower command
 */
public class RemoveLower extends Command {
    private final ModelTree tree;
    private final Builder builder;

    public RemoveLower(ModelTree tree, Builder builder) {
        super("remove_lower {element}", "Удаляет из коллекции все элементы, меньшие, чем заданный");
        this.tree = tree;
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        RemoveLowerRequest request = new RemoveLowerRequest(builder.build(tree));
        RemoveLowerResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getAnswer());
        }
    }
}
