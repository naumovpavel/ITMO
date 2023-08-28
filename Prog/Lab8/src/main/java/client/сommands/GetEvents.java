package client.сommands;

import client.handlers.CollectionPublisher;
import common.Commands;
import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.Event;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Add command
 */
public class GetEvents extends Command {
    private CollectionPublisher publisher;

    public GetEvents(CollectionPublisher publisher) {
        super("get_events", "Добавляет новый элемент в коллекцию");
        this.publisher = publisher;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.GET_EVENTS);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() == Status.OK) {
            List<Pair<Model, Event>> events = response.get("events");
            publisher.setEvents(events);
        }
    }


}
