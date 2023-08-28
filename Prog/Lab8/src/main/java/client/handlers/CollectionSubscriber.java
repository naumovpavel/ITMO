package client.handlers;

import common.models.Model;
import common.utils.Event;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;

public interface CollectionSubscriber {
    void update(List<Pair<Model, Event>> events);
    void setCollection(Collection<Model> collection);
}
