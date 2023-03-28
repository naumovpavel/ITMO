package server.handlers;

import common.models.Model;
import common.models.Person;
import common.utils.ModelTree;
import server.utils.input.JsonLoader;
import server.utils.input.Loader;
import server.utils.output.JsonWriter;
import server.utils.output.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class CollectionHandler<T extends Collection<E>, E extends Model> {
    protected final Date initializationDate;
    protected Class<E> type;
    protected final ModelTree tree;
    protected T collection;
    public CollectionHandler(ModelTree tree) {
        this.initializationDate = new Date();
        this.tree = tree;
    }

    public Date getInitializationDate() {
        return initializationDate;
    }

    public ModelTree getTree() {
        return tree;
    }

    public void load() {
        Loader<E> loader = new JsonLoader<>(tree);
        loader.read(collection);
    }

    public boolean save() {
        Writer<E> writer = new JsonWriter<>(tree);
        return writer.write(collection);
    }
    abstract public T getCollection();

    public Class<E> getType() {
        return type;
    }

    abstract public E getHead();

    public boolean update(Long id, Model obj) {
        if(!removeById(id)) {
            return false;
        }
        add(obj);
        return true;
    }
    abstract public void clear();
    abstract public boolean removeById(Long id);
    abstract public E removeHead();
    abstract public E maxByName();
    abstract public void add(Model obj);
    abstract public int countLess(Person obj);
    abstract public void removeLower(Model obj);

    abstract public E getById(Long id);
    abstract public boolean hasId(Long id);
    abstract public ArrayList<Integer> printFieldDescending();

    abstract public <U extends Model> int countLessThan(U obj);

}
