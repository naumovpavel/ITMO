package client.сommands;

import client.gui.commandsView.ShowView;
import client.gui.frames.MainWindow;
import client.handlers.CollectionHandler;
import client.handlers.CollectionPublisher;
import common.Commands;
import common.models.Model;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.utils.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Show command
 */
public class Show extends Command {
    public MainWindow mainWindow;
    public ModelTree tree;
    private CollectionPublisher publisher;
    public Show(ModelTree tree, CollectionPublisher publisher) {
        super("show", "Выводит все элементы коллекции");
        this.tree = tree;
        this.publisher = publisher;
        this.view = new ShowView(this, tree);
        visible = true;
    }

    public Show(String name, String description) {
        super(name, description);
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.SHOW);
        Response response = client.sendAndReceive(request);
        if(response.getStatus() != Status.OK) {
            view.renderResponse(response);
            return;
        }
        Collection<Model> collection = response.get("collection");
        publisher.setCollection(collection);
        view.renderResponse(response);
    }
}

