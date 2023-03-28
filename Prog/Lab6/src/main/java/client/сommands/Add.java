package client.сommands;

import common.Commands;
import common.request.AddRequest;
import common.request.Request;
import common.response.AddResponse;
import common.response.Response;
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
        AddRequest request = new AddRequest(builder.build(tree));
        AddResponse response = handleResponse(request);

        if(response != null) {
            System.out.println(response.getAnswer());
        }
    }
}
