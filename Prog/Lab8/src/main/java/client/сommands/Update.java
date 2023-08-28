package client.сommands;

import client.gui.commandsView.ChangeView;
import client.utils.input.BuilderStrategy;
import common.Commands;
import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.Converter;
import common.utils.ModelTree;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Add command
 */
public class Update extends Command {
    private final ModelTree tree;
    private final BuilderStrategy builder;
    private Map<String, Object> fields = new HashMap<>();


    public Update(ModelTree tree, BuilderStrategy builder) {
        super("update", "Добавляет новый элемент в коллекцию");
        this.tree = tree;
        this.builder = builder;
        this.view = ChangeView.getInstance();
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2 || args[1].isEmpty() || args[1].isBlank()) {
            view.renderResponse(new Response("Вы не ввели значение id", Status.ERROR));
        }
        int id = Converter.convert(int.class, args[1]);

        Request request = new Request(Commands.GET_BY_ID).put("id", id);
        Response response = client.sendAndReceive(request);

        Model model = null;

        if(response.get("model") == null) {
            view.renderResponse(response);
            return;
        }

        try {
            model = builder.getBuilder().update(tree, response.get("model"));
        } catch (IllegalArgumentException e) {
            view.renderResponse(new Response(e.getMessage(), Status.ERROR));
            return;
        }

        Request updateRequest = new Request(Commands.UPDATE).put("id", id).put("model", model);
        Response updateResponse = client.sendAndReceive(updateRequest);

        view.renderResponse(updateResponse);
    }


}
