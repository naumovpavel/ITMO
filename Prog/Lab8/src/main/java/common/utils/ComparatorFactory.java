package common.utils;

import java.util.Comparator;

public interface ComparatorFactory {
    public <T> Comparator<T> get(Class<T> type, T value);
}
