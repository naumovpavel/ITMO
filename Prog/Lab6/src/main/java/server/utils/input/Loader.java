package server.utils.input;

import common.models.Model;
import common.utils.ModelTree;

import java.util.Collection;

/**
 * Class that loads collection of E type elements
 * @param <E> type
 */
abstract public class Loader<E extends Model> {
    protected ModelTree tree;

    /**
     * Default constructor
     * @param tree tree
     * @see ModelTree
     */
    public Loader(ModelTree tree) {
        this.tree = tree;
    }

    /**
     * Method that reads and returns collection of E type elements
     * @param collection collection of E type elements
     * @param <T> collection type
     */
    abstract public <T extends Collection<E>> void read(T collection);
}
