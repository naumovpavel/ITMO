package utils.output;

import models.Model;
import utils.ModelTree;

import java.util.Collection;

/**
 * Abstract class for writers collection of E type elements
 * @param <E> element type
 */
public abstract class Writer<E extends Model> {
    protected ModelTree tree;

    /**
     * Default constructor
     * @param tree model tree
     */
    public Writer(ModelTree tree) {
        this.tree = tree;
    }

    /**
     * Method that writes collection of E type elements
     * @param collection collection of E type elements
     * @param <T> collection type
     */
    abstract public <T extends Collection<E>> void write(T collection);
}
