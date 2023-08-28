package client.сommands;

import client.utils.input.Builder;
import common.Commands;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;

public class Register extends Command {
    private final ModelTree tree;
    private final Builder builder;

    public Register(ModelTree tree, Builder builder) {
        super("register", "Добавляет новый элемент в коллекцию");
        this.tree = tree;
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.REGISTER).put("user", builder.build(tree));
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.getAnswer());
        client.setToken(response.get("token"));
    }
}
