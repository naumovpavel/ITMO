package common.models;

import common.utils.validators.GraterThan;
import common.utils.validators.NotNull;
import common.utils.validators.StringNotNone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Person model
 */
public class Person extends Model implements Comparable<Person>, Serializable {
    @NotNull
    @StringNotNone
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @GraterThan
    private float height; //Значение поля должно быть больше 0

    @NotNull
    private Color eyeColor; //Поле не может быть null


    public void init(HashMap<String, Object> values) {
        this.name = (String) values.get("name");
        this.eyeColor = (Color) values.get("eyeColor");
        this.height = (Float) values.get("height");
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("eyeColor", eyeColor);
        values.put("height", height);
        return values;
    }

    public Supplier<Person> getConstructorReference() {
        return Person::new;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   name : " + name  +
                ",\n   height : " + height +
                ",\n   eyeColor : " + eyeColor +
                "\n}";
    }

    @Override
    public int compareTo(Person o) {
        return (int) (o.height - height);
    }
}