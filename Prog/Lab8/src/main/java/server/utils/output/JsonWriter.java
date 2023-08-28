package server.utils.output;

import common.models.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import common.utils.ModelTree;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * Class that writes collection of E type elements to Json file
 * @param <E> element type
 */
public class JsonWriter<E extends Model> extends Writer<E> {

    /**
     * Default constructor
     * @param tree model tree
     */
    public JsonWriter(ModelTree tree) {
        super(tree);
    }

    /**
     * Method that writes collection of E type elements to Json file,that gets from lab5 envKey, using BufferedReader
     * @param collection collection of E type elements
     * @param <T> collection type
     */
    @Override
    public <T extends Collection<E>> boolean write(T collection) {
        FileWriter out = null;

        try {
            String fileName = new File(".","data.json").getAbsolutePath();
            if(System.getenv("lab6") != null) {
                fileName = System.getenv("lab6");
            }
            out = new FileWriter(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл");
            return false;
        } catch (IOException e) {
            System.out.println("Не прочитать файл");
            return false;
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for(E element : collection) {
            jsonObject.accumulate("collection", build(tree, element));
        }

        try {
            out.write(jsonObject.toString());
            out.close();
        } catch (IOException e) {
            System.out.println("Не удалось сохранить файл");
            return false;
        }

        return true;
    }


    private <T extends Model> JSONObject build(ModelTree tree, T object) {
        JSONObject jo = new JSONObject();
        HashMap<String, Object> values = object.getValues();
        for(var field : tree.getFields()) {
            if(!field.isPrimitive()) {
                jo.accumulate(field.getName(), build(field, (Model) values.get(field.getName())));
            } else {
                jo.accumulate(field.getName(), values.get(field.getName()));
            }
        }
        return jo;
    }
}
