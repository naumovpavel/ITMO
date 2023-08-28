package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.ModelTree;
import client.utils.input.Builder;

/**
 * Remove lower command
 */
public class RemoveLower extends Command {
    private final ModelTree tree;
    private final Builder builder;

    public RemoveLower(ModelTree tree, Builder builder) {
        super("remove_lower", "  {element} Удаляет из коллекции все элементы, меньшие, чем заданный");
        this.tree = tree;
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.REMOVE_LOWER).put("model", builder.build(tree));
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.getAnswer());
    }
}
