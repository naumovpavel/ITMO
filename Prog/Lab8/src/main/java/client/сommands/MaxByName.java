package client.сommands;

import client.gui.commandsView.MaxByNameView;
import client.handlers.CollectionPublisher;
import common.Commands;
import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;

import java.util.ArrayList;

/**
 * Max by name command
 */
public class MaxByName extends Command {

    private CollectionPublisher publisher;

    public MaxByName(CollectionPublisher publisher) {
        super("max_by_name", "Выводит любой объект из коллекции, значение поля name которого является максимальным");
        this.publisher = publisher;
        view = new MaxByNameView(this);
        visible = true;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.MAX_BY_NAME);
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
