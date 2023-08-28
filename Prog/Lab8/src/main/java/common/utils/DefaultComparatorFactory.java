package common.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Supplier;

public class DefaultComparatorFactory implements ComparatorFactory {

    private static HashMap<Class<?>, Supplier<Comparator<?>>> comparatorBuilders = new HashMap<>();

//    static {
//        comparatorBuilders.put(Integer.class, (Integer x) -> new Comparator<Integer>() {
//
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return 0;
//            }
//        });
//    }

    class ParameterComparator<T extends Comparable> implements Comparable<T> {
        private T to;


        @Override
        public int compareTo(T o) {
            return to.compareTo(o);
        }
    }

    @Override
    public <T> Comparator<T> get(Class<T> type, T value) {
        return null;
    }
}
