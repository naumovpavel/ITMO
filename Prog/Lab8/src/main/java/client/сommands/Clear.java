package client.сommands;

import client.gui.commandsView.ClearView;
import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Clear command
 */
public class Clear extends Command {

    public Clear() {
        super("clear", "Очищает коллекцию");
        visible = true;
        view = new ClearView(this);
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.CLEAR);
        Response response = client.sendAndReceive(request);

        view.renderResponse(response);
    }
}
