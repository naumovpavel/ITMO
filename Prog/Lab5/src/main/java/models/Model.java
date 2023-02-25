package models;

import utils.validators.Validatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Abstract class for models
 */
public abstract class Model {
    protected static Long maxId = 1L;

    /**
     * Returns reference to models constructor
     * @return reference to models constructor
     */
    abstract public Supplier<? extends Model> getConstructorReference();

    /**
     * Method that initials models object with fully validated values(it's safe to cast types)
     * @param values values
     */
    abstract public void init(HashMap<String, Object> values);

    /**
     * Method that returns all models object fields values
     * @return models object fields values
     */
    abstract public HashMap<String, Object> getValues();

    /**
     * returns models object id
     * @return id
     */
    public Long getId() {
        return maxId;
    }
}
