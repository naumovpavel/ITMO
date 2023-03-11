package utils.input;

import models.Model;
import utils.ModelTree;

/**
 * Builder interface for building models
 * @see models.Model
 */
public interface Builder {
    /**
     * Build fully initialized and validated model object
     * @param tree ModelTree
     * @return object of Model
     * @param <T> Model type
     */
    <T extends Model> T build(ModelTree tree);
    <T extends Model> void update(ModelTree tree, T object);
}
