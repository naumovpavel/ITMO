package client.utils.input;

import common.models.Model;
import common.utils.Converter;
import common.utils.ModelTree;
import common.utils.validators.Validator;

import java.util.HashMap;
import java.util.Objects;

public class GUIBuilder implements Builder {
    private Reader reader;

    /**
     * Default constructor
     */
    public GUIBuilder(Reader reader) {
        this.reader = reader;
    }

    @Override
    public void setReader(Reader reader) {
        this.reader = reader;
    }

    /**
     * Build full initialized and validated model object using Cli and sets id
     * @param tree ModelTree
     * @return object of Model
     * @param <T> Model type
     */
    @SuppressWarnings("unchecked")
    public <T extends Model> T build(ModelTree tree) throws IllegalArgumentException {
        T obj = (T) tree.constructor.get();
        HashMap<String, Object> values = new HashMap<>();

        for(ModelTree field : tree.getFields()) {
            if(field.isIgnoreInput()) {
                continue;
            }

            if(!field.isPrimitive()) {
                values.put(field.getName(), build(field));
                continue;
            }

            askValue(field, values);
        }
        obj.init(values);
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public <T extends Model> T update(ModelTree tree, T object) {
        HashMap<String, Object> values = object.getValues();

        for(ModelTree field : tree.getFields()) {
            if(field.isIgnoreInput()) {
                continue;
            }

            if(!field.isPrimitive()) {
                update(field, (Model) values.get(field.getName()));
                continue;
            }

            askValue(field, values);

        }
        object.init(values);
        return object;
    }

    private void askValue(ModelTree field, HashMap<String, Object> values) {
            String value = reader.nextLine();

            if(value.strip().equals("") && field.isMayNull()) {
                values.put(field.getName(), Converter.convert(field.getType(), null));
                return;
            }

            values.put(field.getName(), checkForValid(field, value));
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

    private void printEnumConstant(ModelTree field) {
        System.out.println("Доступные варианты констант для " + field.getName());
        for(Object x : field.getEnumConstants().values()) {
            System.out.println(x);
        }
    }
}