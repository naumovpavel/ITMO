package server.handlers;

import common.models.Model;
import common.utils.Event;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventHandler {
    private CopyOnWriteArrayList<Pair<? extends Model, Event>> events = new CopyOnWriteArrayList<>();
    private ConcurrentHashMap<Integer, Integer> userLastEvent = new ConcurrentHashMap<>();

    public EventHandler() {
    }

    public void addEvent(Model model, Event event) {
        System.out.println(event);
        events.add(new ImmutablePair<>(model, event));
    }

    public List<Pair<? extends Model, Event>> getEvents(Integer id) {
        if(userLastEvent.containsKey(id)) {
            int last = userLastEvent.get(id);
            var eventsForUser = events.stream().skip(last).toList();
            userLastEvent.put(id, last + eventsForUser.size());
            return eventsForUser;
        }
        return new ArrayList<>();
    }

    public void setUserLastEvent(Integer id) {
        userLastEvent.put(id, events.size());
    }
}
