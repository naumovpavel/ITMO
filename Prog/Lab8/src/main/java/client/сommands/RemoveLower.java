package client.сommands;

import client.gui.commandsView.RemoveLowerView;
import client.utils.input.BuilderStrategy;
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
    private final BuilderStrategy builder;

    public RemoveLower(ModelTree tree, BuilderStrategy builder) {
        super("remove_lower", "  {element} Удаляет из коллекции все элементы, меньшие, чем заданный");
        this.tree = tree;
        this.builder = builder;
        visible = true;
        view = new RemoveLowerView(this, tree, builder);
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        try {
            Request request = new Request(Commands.COUNT_LESS_THAN_AUTHOR).put("model", builder.getBuilder().build(tree));
            Response response = client.sendAndReceive(request);
            view.renderResponse(response);
        } catch (IllegalArgumentException e) {
            view.renderResponse(new Response(e.getMessage(), Status.ERROR));
        }
    }
}
