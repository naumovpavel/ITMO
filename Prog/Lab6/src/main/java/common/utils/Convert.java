package common.utils;

/**
 * Functional interface for converting string value to T type
 * @param <T> type
 */
@FunctionalInterface
public interface Convert<T> {
     T convert(Class<T> type, String value) throws Exception;
}
