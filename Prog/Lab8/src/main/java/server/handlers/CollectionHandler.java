package server.handlers;

import common.models.Model;
import common.models.Person;
import common.utils.ModelTree;
import server.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class CollectionHandler<T extends Collection<E>, E extends Model> {
    protected final Date initializationDate;
    protected Class<E> type;
    protected final ModelTree tree;
    protected T collection;
    protected EventHandler eventHandler;
    protected DatabaseManager<E> databaseManager;

    public CollectionHandler(ModelTree tree) {
        this.initializationDate = new Date();
        this.tree = tree;
        this.databaseManager = new DatabaseManager<>(tree);
        this.eventHandler = new EventHandler();
    }

    public Date getInitializationDate() {
        return initializationDate;
    }

    public ModelTree getTree() {
        return tree;
    }

    public void load() {
        databaseManager.load(collection);
    }
    abstract public T getCollection();

    public Class<E> getType() {
        return type;
    }

    abstract public E getHead();

    abstract public boolean update(int id, Model obj, int userId);
    abstract public boolean clear(int userId);
    abstract public boolean removeById(int id, int userId);
    abstract public E removeHead(int userId);
    abstract public E maxByName();
    abstract public boolean add(Model obj, int userId);
    abstract public int countLess(Person obj);
    abstract public boolean removeLower(Model obj, int userId);

    abstract public E getById(int id);
    abstract public boolean hasId(int id, int userId);
    abstract public ArrayList<Integer> printFieldDescending();

    abstract public <U extends Model> int countLessThan(U obj);

    abstract public T show(int id);

    public EventHandler getEventHandler() {
        return eventHandler;
    }
}
