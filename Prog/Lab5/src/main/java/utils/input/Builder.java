package utils.input;

import models.Model;
import utils.ModelTree;

public interface Builder {
    <T extends Model> T build(ModelTree tree);
    <T extends Model> T build(ModelTree tree, Long id);
}
