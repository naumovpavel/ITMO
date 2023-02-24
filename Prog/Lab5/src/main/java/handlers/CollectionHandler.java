package handlers;

import models.Model;
import models.Person;
import utils.*;
import utils.input.JsonLoader;
import utils.input.Loader;
import utils.output.JsonWriter;
import utils.output.Writer;

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

    public void save() {
        Writer<E> writer = new JsonWriter<>(tree);
        writer.write(collection);
    }
    abstract public T getCollection();

    public Class<E> getType() {
        return type;
    }

    abstract public E getHead();
    abstract public void clear();
    abstract public boolean removeById(Long id);
    abstract public E removeHead();
    abstract public E maxByName();
    abstract public void add(E obj);
    abstract public int countLess(Person obj);
    abstract public void removeLower(E obj);

    abstract public void update(E obj, Long id);
    abstract public boolean hasId(Long id);
    abstract public void printFieldDescending();

    abstract public <U extends Model> int countLessThan(U obj);

}
