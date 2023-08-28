package client.сommands;

import client.gui.commandsView.ChangeView;
import client.utils.input.Builder;
import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.Converter;
import common.utils.ModelTree;

public class GetById extends Command{

    public GetById() {
        super("get_by_id", "Добавляет новый элемент в коллекцию");
        this.view = ChangeView.getInstance();
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2 || args[1].isEmpty() || args[1].isBlank()) {
            view.renderResponse(new Response("Вы не ввели значение id", Status.ERROR));
            return;
        }
        int id = 0;

        try {
            id = Converter.convert(int.class, args[1]);
        } catch (IllegalArgumentException e) {
            view.renderResponse(new Response(e.getMessage(), Status.ERROR));
            return;
        }


        Request request = new Request(Commands.GET_BY_ID).put("id", id);
        Response response = client.sendAndReceive(request);

        view.renderResponse(response);
    }
}
