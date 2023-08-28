package client.handlers;

import client.gui.frames.MainWindow;
import client.gui.ui.Table;
import client.network.Client;
import client.network.TcpClient;
import client.сommands.CommandManager;
import common.models.Model;
import common.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class CollectionHandler implements CollectionPublisher {
    private final CopyOnWriteArrayList<CollectionSubscriber> subscribers = new CopyOnWriteArrayList<>();
    private CommandManager manager;
    private List<Pair<Model, Event>> events = new ArrayList<>();
    private static CollectionHandler instance = null;
    private ReentrantLock lock = new ReentrantLock();


    public CollectionHandler(CommandManager manager) {
        this.manager = manager;
    }

    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        manager.executeCommand("get_events");
    }

    @Override
    public void addSubscriber(CollectionSubscriber subscriber) {
        lock.lock();
        subscribers.add(subscriber);
        lock.unlock();
    }

    @Override
    public void removeSubscriber(CollectionSubscriber subscriber) {
        lock.lock();
        subscribers.removeIf(x -> x == subscriber);
        lock.unlock();
    }

    @Override
    public void setEvents(List<Pair<Model, Event>> events) {
        lock.lock();
        this.events = events;
        lock.unlock();
        notifySubscribers();
    }

    @Override
    public void setCollection(Collection<Model> collection) {
        lock.lock();
        for(var subscriber : subscribers) {
            subscriber.setCollection(collection);
        }
        lock.unlock();
    }

    private void notifySubscribers() {
        lock.lock();
        for(var subscriber : subscribers) {
            subscriber.update(events);
        }
        lock.unlock();
        run();
    }
}
