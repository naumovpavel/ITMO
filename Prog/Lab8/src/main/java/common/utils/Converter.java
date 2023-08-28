package common.utils;

import client.main.Main;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for converting string values to required type
 */
public class Converter {
    private static final HashMap<Class<?>, Convert> converters = new HashMap<>();
    private static ResourceBundle r = ResourceBundle.getBundle("bundles.Errors");

    static {
        converters.put(String.class, (type, x) -> (String)x);
        converters.put(Float.class, (type, x) -> Float.valueOf(x));
        converters.put(Integer.class, (type, x) -> Integer.valueOf(x));
        converters.put(Long.class, (type, x) -> Long.valueOf(x));
        converters.put(int.class, (type, x) -> Integer.valueOf(x));
        converters.put(double.class, (type, x) -> Double.valueOf(x));
        converters.put(float.class, (type, x) -> Float.valueOf(x));
        converters.put(long.class, (type, x) -> Long.valueOf(x));
        converters.put(Double.class, (type, x) -> Double.valueOf(x));
        converters.put(Date.class, (type, x) -> (new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Main.locale).parse(x)));
    }

    /**
     * Converts string value to required type
     * @param type type
     * @param value value
     * @return converted value
     * @param <T> type
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> type, String value) {
        if(type.isEnum())
            return getEnum(type, value);
        if(value == null || !converters.containsKey(type)) {
            return getNull(type);
        }

        try {
            return (T) killInfinity(converters.get(type).convert(type, value));
        } catch (Exception e) {
            throw new IllegalArgumentException(r.getString("format_error") + " " + value);
        }
    }

    private static <T> T getEnum(Class<T> type, String value) {
        var val = Arrays.stream(type.getEnumConstants()).filter(x -> x.toString().equals(value)).findFirst();
        if(val.isPresent())
            return val.get();
        else
            throw new IllegalArgumentException(r.getString("constant_error"));
    }

    /**
     * Return null representation of required type
     * @param type type
     * @return null value
     * @param <T> type
     */
    @SuppressWarnings("unchecked")
    private static <T> T getNull(Class<T> type) {
        if(type.isPrimitive()) {
            try {
                return (T) converters.get(type).convert(type, "0");
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Return max value if value is infinite else return itself
     * @return value without infinite
     * @param value value
     */
    private static Object killInfinity(Object value) {
        if(value instanceof Float && Float.isInfinite((float) value)) {
            return Float.MAX_VALUE;
        } else if(value instanceof Double && Double.isInfinite((double) value)) {
            return Double.MAX_VALUE;
        }
        return value;
    }

    /**
     * Checks if type is primitive
     * @param type type
     * @return true if type is primitive
     * @param <T> type
     */
    public static <T> boolean checkForPrimitive(Class<T> type) {
        return converters.containsKey(type) || type.isEnum() || type.isArray();
    }
}
