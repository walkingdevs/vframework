package brains.vframework.helpers;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class Fields implements Serializable {

    private final static Map<Class<?>, List<Field>> cache = new HashMap<>();

    public static List<Field> ofClass(final Class<?> _class) {
        if (!cache.containsKey(_class)) {
            cache.put(_class, ofClass(new ArrayList<>(), _class));
        }
        return cache.get(_class);
    }

    private static List<Field> ofClass(final List<Field> fields, final Class<?> _class) {
        Collections.addAll(fields, _class.getDeclaredFields());

        if (_class.getSuperclass() != null) {
            return ofClass(fields, _class.getSuperclass());
        }

        return fields;
    }
}
