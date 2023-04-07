package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.ModelTree;
import client.utils.input.Builder;

/**
 * Add command
 */
public class Add extends Command {
    private final ModelTree tree;
    private final Builder builder;

    public Add(ModelTree tree, Builder builder) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.tree = tree;
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.ADD).put("model", builder.build(tree));
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.getAnswer());
    }
}
