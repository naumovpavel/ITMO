package server.utils.input;

import common.utils.Converter;
import common.utils.ModelTree;
import common.utils.validators.Validator;
import common.models.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Class that loads collection of E type elements from Json file using BufferedReader
 * @param <E>
 */
public class JsonLoader<E extends Model> implements Loader<E> {
    private ModelTree tree;
    /**
     * Default constructor
     * @param tree tree
     */
    public JsonLoader(ModelTree tree) {
        this.tree = tree;
    }

    /**
     * Method that read and return collection of E type elements from Json file,that gets from lab5 envKey, using BufferedReader
     * @param collection collection of E type elements
     * @param <T> collection type
     */
    @Override
    public <T extends Collection<E>> void load(T collection) {
        BufferedReader in = null;
        JSONObject jo = null;

        try {
            if(System.getenv("lab6") == null) {
                System.out.println("Не задана переменная окружения lab6. Коллекция не загружена");
                return;
            }
            in = new BufferedReader(new FileInputStream(System.getenv("lab6")));
            jo = new JSONObject(in.getStringInput());
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл");
            return;
        } catch (JSONException e) {
            System.out.println("Файл заполнен не правильно");
            return;
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл");
            return;
        }

        try {
            JSONArray values = jo.getJSONArray("collection");
            for(int i = 0; i < values.length(); i++) {
                collection.add(build(tree, values.getJSONObject(i)));
            }
            validateCollection(collection);
        } catch (JSONException e) {
            System.out.println("Файл заполнен не правильно");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that  build fully initialized and validated model object from Json file
     * @param tree model tree
     * @param jo JsonObject
     * @param <T> element type
     */
    @SuppressWarnings("unchecked")
    private  <T extends Model> T build(ModelTree tree, JSONObject jo) throws IllegalArgumentException {
        T obj = (T) tree.constructor.get();
        HashMap<String, Object> values = new HashMap<>();

        for(ModelTree field : tree.getFields()) {
            if(field.isPrimitive()) {
                Object value;
                try {
                    value = jo.get(field.getName());
                } catch (JSONException e) {
                    if(field.isMayNull()) {
                        values.put(field.getName(), Converter.convert(field.getType(), null));
                        continue;
                    } else {
                        throw new IllegalArgumentException("Не найдено поле " + field.getName());
                    }
                }

                if(jo.isNull(field.getName())) {
                    values.put(field.getName(), Converter.convert(field.getType(), null));
                    try {
                        Validator.validate(values.get(field.getName()), field.getValidators());
                        continue;
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Проблемы с полем " + field.getName() + ". " + e.getMessage());
                    }
                }

                if(field.isEnum()) {
                    if(field.getEnumConstants().containsKey(value.toString())) {
                        values.put(field.getName(), field.getEnumConstants().get(value.toString()));
                    } else {
                        throw new IllegalArgumentException("Найдена не существующая константа");
                    }
                } else {
                    values.put(field.getName(), Converter.convert(field.getType(), value.toString()));
                    if (field.getValidators() != null) {
                        try {
                            Validator.validate(values.get(field.getName()), field.getValidators());
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Проблемы с полем " + field.getName() + ". " + e.getMessage());
                        }
                    }
                }
            } else {
                JSONObject value = null;
                try {
                    value = jo.getJSONObject(field.getName());
                } catch (JSONException e) {
                    if(!field.isMayNull())  {
                        throw new IllegalArgumentException("Не найдено поле " + field.getName());
                    }
                }
                values.put(field.getName(), build(field, value));
            }
        }
        obj.init(values);
        return (T) obj;
    }

    /**
     * Method that checks that collection has only unique ids
     * @param collection collection
     * @param <T> collection type
     * @throws IllegalArgumentException throws if method found 2 same id
     */
    private <T extends Collection<E>> void validateCollection(Collection<E> collection) throws IllegalArgumentException {
        HashSet<Integer> ids = new HashSet<>();
        for(var x : collection) {
            if(ids.contains(x.getId())) {
                throw new IllegalArgumentException("Получено два объекта с одинаковым id = " + x.getId());
            } else {
                ids.add(x.getId());
            }
        }
    }
}
