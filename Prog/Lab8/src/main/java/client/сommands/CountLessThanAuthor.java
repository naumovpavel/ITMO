package client.сommands;

import client.gui.commandsView.CountLessThanAuthorView;
import client.utils.input.BuilderStrategy;
import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.ModelTree;
import client.utils.input.Builder;
import common.models.Person;

/**
 * Count less than author command
 */
public class CountLessThanAuthor extends Command {
    private final ModelTree tree;
    private final BuilderStrategy builder;

    public CountLessThanAuthor(BuilderStrategy builder) {
        super("count_less_than_author", " author Выводит количество элементов, значение поля author которых меньше заданного");
        this.tree = new ModelTree(Person.class);
        this.builder = builder;
        visible = true;
        view = new CountLessThanAuthorView(this, tree, builder);
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        try {
            Request request = new Request(Commands.COUNT_LESS_THAN_AUTHOR).put("model", builder.getBuilder().build(tree));
            Response response = client.sendAndReceive(request);
            view.renderResponse(response);
        } catch (IllegalArgumentException e) {
            view.renderResponse(new Response(e.getMessage(), Status.ERROR));
            return;
        }
    }
}
