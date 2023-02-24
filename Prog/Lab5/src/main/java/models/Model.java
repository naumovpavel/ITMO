package models;

import utils.validators.Validatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public abstract class Model {
    //public HashMap<String, ArrayList<Validatable>> contract;
    protected static Long maxId = 1L;

    abstract public Supplier<? extends Model> getConstructorReference();
    abstract public void init(HashMap<String, Object> values);
    abstract public HashMap<String, Object> getValues();
    abstract public Long getId();
}
