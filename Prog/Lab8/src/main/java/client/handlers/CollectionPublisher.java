package client.handlers;

import common.models.Model;
import common.utils.Event;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;

public interface CollectionPublisher {
    void addSubscriber(CollectionSubscriber subscriber);
    void removeSubscriber(CollectionSubscriber subscriber);
    void setEvents(List<Pair<Model, Event>> events);
    void setCollection(Collection<Model> collection);
    void run();
}
