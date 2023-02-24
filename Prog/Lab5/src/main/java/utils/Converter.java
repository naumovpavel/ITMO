package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Converter {
    private static final HashMap<String, Convert> converters = new HashMap<>();

    static {
        converters.put(String.class.getTypeName(), (type, x) -> (String)x);
        converters.put(Float.class.getName(), (type, x) -> Float.valueOf(x));
        converters.put(Integer.class.getName(), (type, x) -> Integer.valueOf(x));
        converters.put(Long.class.getName(), (type, x) -> Long.valueOf(x));
        converters.put("int", (type, x) -> Integer.valueOf(x));
        converters.put("double", (type, x) -> Double.valueOf(x));
        converters.put("float", (type, x) -> Float.valueOf(x));
        converters.put("long", (type, x) -> Long.valueOf(x));
        converters.put(Double.class.getName(), (type, x) -> Double.valueOf(x));
        converters.put(Date.class.getName(), (type, x) -> (new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy",  Locale.ENGLISH).parse(x)));
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> type, String value) {
        if(value == null) {
            return getNull(type);
        }
        try {
            if (converters.containsKey(type.getName())) {
                return (T) converters.get(type.getName()).convert(type, value);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Вы ввели не допустимое значение " + value);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getNull(Class<T> type) {
        try {
            if (converters.containsKey(type.getName())) {
                return (T) converters.get(type.getName()).convert(type, null);
            }
        } catch (Exception e) {
            try {
                if (converters.containsKey(type.getName())) {
                    return (T) converters.get(type.getName()).convert(type, "0");
                }
            } catch (Exception ex) {
                return null;
            }

        }
        return null;
    }

    public static <T> boolean checkForPrimitive(Class<T> type) {
        return converters.containsKey(type.getName()) || type.isEnum();
    }
}
