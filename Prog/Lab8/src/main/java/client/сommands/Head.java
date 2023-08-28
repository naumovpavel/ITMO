package client.сommands;

import client.gui.commandsView.HeadView;
import client.handlers.CollectionPublisher;
import common.Commands;
import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;

import java.util.ArrayList;

/**
 * Head command
 */
public class Head extends Command {
    private CollectionPublisher publisher;

    public Head(CollectionPublisher publisher) {
        super("head", "Выводит первый элемент в коллекции");
        view = new HeadView(this);
        visible = true;
        this.publisher = publisher;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.HEAD);
        Response response = client.sendAndReceive(request);

        view.renderResponse(response);

        if(response.getStatus() != Status.OK) {
            view.renderResponse(response);
            return;
        }

        ArrayList<Model> collection = new ArrayList<>();
        collection.add(response.get("model"));
        publisher.setCollection(collection);
    }
}
