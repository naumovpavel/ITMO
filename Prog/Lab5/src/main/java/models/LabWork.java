package models;

import utils.validators.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Supplier;


public class LabWork extends Model implements Comparable<LabWork> {
    @NotNull
    @GraterThan
    @AutoGenerated
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @StringNotNone
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private Coordinates coordinates; //Поле не может быть null
    @NotNull
    @AutoGenerated
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @GraterThan
    private long minimalPoint; //Значение поля должно быть больше 0
    private long tunedInWorks;
    @NotNull
    private Difficulty difficulty; //Поле может быть null
    @NotNull
    private Person author; //Поле не может быть null



    @Override
    public void init(HashMap<String, Object> values) {
        if(values.containsKey("id")) {
            this.id = (Long) values.get("id");
            LabWork.maxId = Long.max(id + 1 , maxId);
        } else {
            this.id = maxId++;
        }
        if(values.containsKey("creationDate")) {
            this.creationDate = (Date) values.get("creationDate");
        } else {
            this.creationDate = new Date();
        }
        this.name = (String) values.get("name");
        this.coordinates = (Coordinates) values.get("coordinates");
        this.difficulty = (Difficulty) values.get("difficulty");
        this.author = (Person) values.get("author");
        this.minimalPoint = (long) values.get("minimalPoint");
        this.tunedInWorks = (long) values.get("tunedInWorks");
    }

    public Person getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTunedInWorks() {
        return tunedInWorks;
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("id", id);
        values.put("creationDate", creationDate);
        values.put("coordinates", coordinates);
        values.put("difficulty", difficulty);
        values.put("author", author);
        values.put("minimalPoint", minimalPoint);
        values.put("tunedInWorks", tunedInWorks);
        return values;
    }
    @Override
    public Supplier<? extends Model> getConstructorReference() {
        return LabWork::new;
    }

    @Override
    public String toString() {
        return "{\n" +
                "id : " + id +
                ",\nname : " + name +
                ",\ncoordinates : " + coordinates +
                ",\ncreationDate : " + creationDate +
                ",\nminimalPoint : " + minimalPoint +
                ",\ntunedInWorks : " + tunedInWorks +
                ",\ndifficulty : " + difficulty +
                ",\nauthor : " + author +
                "\n}\n";
    }

    @Override
    public int compareTo(LabWork o) {
        return (int)(o.getId() - id);
    }
}