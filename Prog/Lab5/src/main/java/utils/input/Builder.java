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

    /**
     * Build full initialized and validated model object and set id
     * @param tree ModelTree
     * @param id id
     * @return object of Model
     * @param <T> Model type
     */
    <T extends Model> T build(ModelTree tree, Long id);

}
