package client.сommands;

import client.gui.commandsView.ChangeView;
import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.Converter;

/**
 * Remove by id command
 */
public class RemoveById extends Command {

    public RemoveById() {
        super("remove_by_id id", "Удаляет элемент с заданным id");
        view = ChangeView.getInstance();
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2 || args[1].isEmpty() || args[1].isBlank()) {
            view.renderResponse(new Response("Вы не ввели значение id", Status.ERROR));
        }
        int id = Converter.convert(int.class, args[1]);
        Request request = new Request(Commands.REMOVE_BY_ID).put("id", id);
        Response response = client.sendAndReceive(request);

        view.renderResponse(response);
    }
}
