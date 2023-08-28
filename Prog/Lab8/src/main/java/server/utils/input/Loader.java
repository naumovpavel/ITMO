package server.utils.input;

import common.models.Model;
import common.utils.ModelTree;

import java.util.Collection;

/**
 * Class that loads collection of E type elements
 * @param <E> type
 */
public interface Loader<E extends Model> {

    /**
     * Method that reads and returns collection of E type elements
     * @param collection collection of E type elements
     * @param <T> collection type
     */
    abstract public <T extends Collection<E>> void load(T collection);
}
