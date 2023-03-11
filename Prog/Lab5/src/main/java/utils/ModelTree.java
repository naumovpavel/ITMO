package utils;

import utils.validators.*;
import models.Model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

import static java.util.Arrays.sort;

/**
 * Class that stores tree of the model
 */
public class ModelTree {
    private final String name;
    private final Class<?> type;
    public Supplier<? extends Model> constructor;
    private final ArrayList<ModelTree> fields = new ArrayList<>();
    private ArrayList<Validatable> validators;
    private boolean mayNull = true;
    private boolean autoGenerated = false;
    private final boolean primitive;
    private boolean isEnum = false;
    private HashMap<String, Enum<?>> enumConstants;

    /**
     * Creates an object containing the model tree
     * @param name name
     * @param type element type Class
     * @param <T> type of the element
     */
    public <T> ModelTree(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.primitive = Converter.checkForPrimitive(type);

        if(type.isEnum()) {
            isEnum = true;
            enumConstants = new HashMap<>();
            for(T x : type.getEnumConstants()) {
                enumConstants.put(x.toString(), (Enum<?>) x);
            }
        }

        if(!this.primitive) {
            try {
                Model obj = (Model) type.getConstructor().newInstance();
                this.constructor = obj.getConstructorReference();
                for(Field field : type.getDeclaredFields()) {
                    this.fields.add(new ModelTree(field.getName(), field.getType(), findValidators(field)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        fields.sort(Comparator.comparing((x) -> x.name));
    }

    public ModelTree(String name, Class<?> type, ArrayList<Validatable> validators) {
        this(name, type);
        this.validators = validators;
        for(Validatable validatable : validators) {
            if(validatable instanceof NotNullValidator) {
                this.mayNull = false;
            }
            if(validatable instanceof AutoGeneratedValidator) {
                this.autoGenerated = true;
            }
        }
    }

    private ArrayList<Validatable> findValidators(Field field) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ArrayList<Validatable> validatables = new ArrayList<>();
        Annotation[] annotations = field.getAnnotations();

        for(Annotation annotation : annotations) {
            ValidatableAnnotation validatableAnnotation = annotation.annotationType().getAnnotation(ValidatableAnnotation.class);
            if(validatableAnnotation != null) {
                Validatable validatable = validatableAnnotation.validator().getConstructor().newInstance();
                if(annotation instanceof GraterThan) {
                    validatable = ((ValidatableWithArg)validatable).getInstance(((GraterThan)annotation).value());
                } else if(annotation instanceof LessThan) {
                    validatable = ((ValidatableWithArg)validatable).getInstance(((LessThan)annotation).value());
                } else {
                    validatable = validatable.getInstance();
                }
                validatables.add(validatable);
            }
        }

        return validatables;
    }

    /**
     * Creates an object containing the model tree and set name to class name of the type
     * @param type element type Class
     * @param <T> type of the element
     */
    public <T> ModelTree(Class<T> type) {
        this(type.getName(), type);
    }

    /**
     * Returns nodes name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if node can be null
     * @return isMayNull
     */
    public boolean isMayNull() {
        return mayNull;
    }

    /**
     * Returns true if node is auto generating
     * @return isAutoGenerated
     */
    public boolean isAutoGenerated() {
        return autoGenerated;
    }

    /**
     * Returns type of node
     * @return type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Returns reference to model constructor
     * @return reference to model constructor
     */
    public Supplier<? extends Model> getConstructor() {
        return constructor;
    }

    /**
     * Returns true if node type is primitive
     * @return isPrimitive
     */
    public boolean isPrimitive() {
        return primitive;
    }

    /**
     * Returns models fields
     * @return ArrayList of fields(field also model tree)
     */
    public ArrayList<ModelTree> getFields() {
        return fields;
    }

    /**
     * Returns true if node type is Enum
     * @return isEnum
     */
    public boolean isEnum() {
        return isEnum;
    }

    /**
     * Returns validators for models fields
     * @return HashMap of validators
     * @see Validatable
     */
    public ArrayList<Validatable> getValidators() {
        return validators;
    }

    /**
     * Returns enum constants if node is Enum
     * @return HashMap of enum constants
     */
    public HashMap<String, Enum<?>> getEnumConstants() {
        return enumConstants;
    }

    /**
     * Prints model tree
     * @param tree tree
     * @param tab indent
     */
    public static void print(ModelTree tree, String tab) {
        System.out.println(tab + tree.name + " " + tree.mayNull);
        if(!tree.primitive) {
            for(ModelTree x : tree.fields) {
                ModelTree.print(x, tab + "  ");
            }
        }
    }
}
