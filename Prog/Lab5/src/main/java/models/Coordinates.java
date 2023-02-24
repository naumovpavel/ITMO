package models;

import utils.validators.GraterThan;
import utils.validators.LessThan;
//import utils.validators.LessThanValidator;
import utils.validators.Validatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class Coordinates extends Model {
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

    @Override
    public Long getId() {
        return null;
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