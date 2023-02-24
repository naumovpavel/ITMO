package utils.output;

import models.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.ModelTree;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

public class JsonWriter<E extends Model> extends Writer<E> {

    public JsonWriter(ModelTree tree) {
        super(tree);
    }

    @Override
    public <T extends Collection<E>> void write(T collection) {
        FileWriter out = null;
        try {
            if(System.getenv("lab5") == null) {
                System.out.println("Не задана переменная окружения lab5. Коллекция не загружена");
                return;
            }
            out = new FileWriter(System.getenv("lab5"));
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл");
            return;
        } catch (IOException e) {
            System.out.println("Не прочитать файл");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        //jsonObject.append("collection");
        JSONArray jsonArray = new JSONArray();

        for(E element : collection) {
            //jsonArray.put(build(tree, element));
            jsonObject.accumulate("collection", build(tree, element));
        }
        //jsonObject.append("collection", jsonArray);
        try {
            //System.out.println(jsonObject.toString());
            out.write(jsonObject.toString());
            out.close();
        } catch (IOException e) {
            System.out.println("Не удалось сохранить файл");
        }

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
