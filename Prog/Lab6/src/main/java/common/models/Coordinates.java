package common.models;

import common.utils.validators.GraterThan;
import common.utils.validators.LessThan;
//import utils.validators.LessThanValidator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Coordinates model
 */
public class Coordinates extends Model implements Serializable {
    @GraterThan("-73")
    private long x; //Значение поля должно быть больше -73
    @LessThan("865")
    private long y; //Максимальное значение поля: 865

    @Override
    public void init(HashMap<String, Object> values) {
        this.x = (long) values.get("x");
        this.y = (long) values.get("y");
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("x", x);
        values.put("y", y);
        return values;
    }

    public Supplier<Coordinates> getConstructorReference() {
        return Coordinates::new;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   x : " + x +
                ",\n   y : " + y +
                "\n}";
    }
}