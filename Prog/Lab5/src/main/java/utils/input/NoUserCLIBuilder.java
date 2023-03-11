package utils.input;

import models.Model;
import utils.Converter;
import utils.ModelTree;
import utils.validators.Validator;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class that can build model objects from script file
 */
public class NoUserCLIBuilder implements Builder {
    private final Reader reader;

    public NoUserCLIBuilder(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Build fully initialized and validated model object from file and sets id from script file
     * @param tree ModelTree
     * @return object of Model
     * @param <T> Model type
     */
    @SuppressWarnings("unchecked")
    public <T extends Model> T build(ModelTree tree) {
        T obj = (T) tree.constructor.get();
        HashMap<String, Object> values = new HashMap<>();

        for(ModelTree field : tree.getFields()) {
            if(field.isAutoGenerated()) {
                continue;
            }

            if(!field.isPrimitive()) {
                if(field.isMayNull() && askQuestion()) {
                    values.put(field.getName(), null);
                } else {
                    values.put(field.getName(), build(field));
                }
                continue;
            }

            askValue(field, values);
        }
        obj.init(values);
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public <T extends Model> void update(ModelTree tree, T object) {
        HashMap<String, Object> values = object.getValues();

        for(ModelTree field : tree.getFields()) {
            if(field.isAutoGenerated()) {
                continue;
            }

            if(!askQuestion()) {
                continue;
            }

            if(!field.isPrimitive()) {
                if(field.isMayNull() && askQuestion()) {
                    values.put(field.getName(), null);
                } else {
                    update(field, (Model) values.get(field.getName()));
                }
                continue;
            }

            askValue(field, values);
        }
        object.init(values);
    }

    private boolean askQuestion() throws IllegalArgumentException {
        String answer = reader.nextLine();
        if(!Objects.equals(answer, "N") && !Objects.equals(answer, "Y")) {
            throw new IllegalArgumentException("Я вас не понимать");
        } else {
            return answer.equals("Y");
        }
    }

    private void askValue(ModelTree field, HashMap<String, Object> values) throws IllegalArgumentException {
            String value = reader.nextLine();

            if(value.strip().equals("") && field.isMayNull()) {
                values.put(field.getName(), Converter.convert(field.getType(), null));
            }

            try {
                values.put(field.getName(), checkForValid(field, value));
            }  catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Вы ввели недопустимое значение! " + e.getMessage());
            }
    }

    private Object checkForValid(ModelTree field, String value) throws IllegalArgumentException {
        if(field.isEnum()) {
            if(field.getEnumConstants().containsKey(value)) {
                return field.getEnumConstants().get(value);
            } else {
                throw new IllegalArgumentException("Не существующая константа");
            }
        } else {
            if (field.getValidators() != null) {
                Validator.validate(value, field.getValidators());
            }
            return Converter.convert(field.getType(), value);
        }
    }
}
