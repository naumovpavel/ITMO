package client.сommands;

import client.gui.commandsView.AddView;
import client.utils.input.BuilderStrategy;
import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.ModelTree;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Add command
 */
public class Add extends Command {
    private final ModelTree tree;
    private final BuilderStrategy builder;


    public Add(ModelTree tree, BuilderStrategy builder) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.tree = tree;
        this.builder = builder;
        this.view = new AddView(this, tree, builder);
        visible = true;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.ADD);

        try {
            request.put("model", builder.getBuilder().build(tree));
        } catch (IllegalArgumentException e) {
            view.renderResponse(new Response(e.getMessage(), Status.ERROR));
            return;
        }

        Response response = client.sendAndReceive(request);

        view.renderResponse(response);
    }


}
