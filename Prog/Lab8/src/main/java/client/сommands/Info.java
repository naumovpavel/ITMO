package client.сommands;

import client.gui.commandsView.InfoView;
import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Info command
 */
public class Info extends Command {

    public Info() {
        super("info", "Выводит информацию о коллекции");
        view = new InfoView(this);
        visible = true;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.INFO);
        Response response = client.sendAndReceive(request);

        view.renderResponse(response);
    }
}
