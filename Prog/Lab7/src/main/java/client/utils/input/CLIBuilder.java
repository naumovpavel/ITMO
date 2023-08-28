package client.utils.input;

import common.utils.Converter;
import common.utils.ModelTree;
import common.utils.validators.Validator;
import common.models.Model;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class that can build model objects using Cli
 */
public class CLIBuilder implements Builder {
    private Reader reader;

    /**
     * Default constructor
     */
    public CLIBuilder(Reader reader) {
        this.reader = reader;
    }

    /**
     * Build full initialized and validated model object using Cli and sets id
     * @param tree ModelTree
     * @return object of Model
     * @param <T> Model type
     */
    @SuppressWarnings("unchecked")
    public <T extends Model> T build(ModelTree tree) {
        T obj = (T) tree.constructor.get();
        HashMap<String, Object> values = new HashMap<>();

        for(ModelTree field : tree.getFields()) {
            if(field.isIgnoreInput()) {
                continue;
            }

            if(!field.isPrimitive()) {
                if(field.isMayNull() && askQuestion("Поле " + field.getName() + " не обязательно, пропустить? (Y/N)")) {
                    values.put(field.getName(), null);
                } else {
                    System.out.println("Введите " + field.getName());
                    values.put(field.getName(), build(field));
                }
                continue;
            }

            if(field.isEnum()) {
                printEnumConstant(field);
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
            
            if(!askQuestion("Обновить поле " + field.getName() + "? (Y/N)")) {
                continue;
            }

            if(!field.isPrimitive()) {
                if(field.isMayNull() && askQuestion("Поле " + field.getName() + " не обязательно, пропустить? (Y/N)")) {
                    values.put(field.getName(), null);
                } else {
                    System.out.println("Введите " + field.getName());
                    update(field, (Model) values.get(field.getName()));
                }
                continue;
            }

            if(field.isEnum()) {
                printEnumConstant(field);
            }

            askValue(field, values);

        }
        object.init(values);
        return object;
    }

    private boolean askQuestion(String question) {
        while (true) {
            System.out.println(question);
            String answer = reader.nextLine();
            if(!Objects.equals(answer, "N") && !Objects.equals(answer, "Y")) {
                System.out.println("Я вас не понимать");
            } else {
                return answer.equals("Y");
            }
        }
    }
    
    private void askValue(ModelTree field, HashMap<String, Object> values) {
        while (true) {
            System.out.println("Введите " + field.getName());
            String value = reader.nextLine();

            if(value.strip().equals("") && field.isMayNull()) {
                values.put(field.getName(), Converter.convert(field.getType(), null));
                break;
            }

            try {
                values.put(field.getName(), checkForValid(field, value));
                break;
            }  catch (IllegalArgumentException e) {
                System.out.println("Вы ввели недопустимое значение! " + e.getMessage() + ". Повторите ввод");
            }
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
    
    private void printEnumConstant(ModelTree field) {
        System.out.println("Доступные варианты констант для " + field.getName());
        for(Object x : field.getEnumConstants().values()) {
            System.out.println(x);
        }
    }
}
