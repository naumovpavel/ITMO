package client.сommands;

import client.gui.commandsView.RemoveHeadView;
import client.handlers.CollectionPublisher;
import common.Commands;
import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;

import java.util.ArrayList;

/**
 * Remove head command
 */
public class RemoveHead extends Command {
    private CollectionPublisher publisher;

    public RemoveHead(CollectionPublisher publisher) {
        super("remove_head", "Удаляет первый элемент коллекции");
        this.publisher = publisher;
        view = new RemoveHeadView(this);
        visible = true;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.REMOVE_HEAD);
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
