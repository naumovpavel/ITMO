package utils.input;

import models.Model;
import utils.ModelTree;

import java.util.Collection;

abstract public class Loader<E extends Model> {
    protected ModelTree tree;

    public Loader(ModelTree tree) {
        this.tree = tree;
    }

    abstract public <T extends Collection<E>> void read(T collection);
}
