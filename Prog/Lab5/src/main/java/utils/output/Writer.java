package utils.output;

import models.Model;
import utils.ModelTree;

import java.util.Collection;

public abstract class Writer<E extends Model> {
    protected ModelTree tree;

    public Writer(ModelTree tree) {
        this.tree = tree;
    }

    abstract public <T extends Collection<E>> void write(T collection);
}
