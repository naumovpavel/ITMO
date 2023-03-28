package client.utils.input;

import common.utils.ModelTree;
import common.models.Model;

/**
 * Builder interface for building models
 * @see Model
 */
public interface Builder {
    /**
     * Build fully initialized and validated model object
     * @param tree ModelTree
     * @return object of Model
     * @param <T> Model type
     */
    <T extends Model> T build(ModelTree tree);
    <T extends Model> T update(ModelTree tree, T object);
}
