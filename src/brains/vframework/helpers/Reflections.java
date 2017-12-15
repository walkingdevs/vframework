package brains.vframework.helpers;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class Reflections implements Serializable {

    private final static Map<String, Map<String, Field>> cache = new HashMap<>();

    public static <T> T get(Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        return (T) field.get(object);
    }

    public static <T> T get(String fieldName, Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        return get(field, object);
    }

    public static <T> T newInstance(Class<?> type) throws IllegalAccessException, InstantiationException {
        return (T) type.newInstance();
    }

    public static Class<?> getDataType(Class<?> type, String fieldName) {

        Field field = getField(type, fieldName);
        if (field.getGenericType().getClass().isAssignableFrom(ParameterizedType.class)) {
            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            return (Class<?>) listType.getActualTypeArguments()[0];
        }

        return field.getType();
    }

    public static boolean isAnnotationPresent(Class<?> type, String fieldName, Class<? extends Annotation> annotationClass) {
        Field field = getField(type, fieldName);

        return field != null && field.isAnnotationPresent(annotationClass);
    }

    public static Class<?> getFieldClass(Class<?> type, String name) {
        return getField(type, name).getType();
    }

    public static Field getField(Class<?> type, String fieldName) {
        if (getAllFields(type).containsKey(fieldName)) {
            return getAllFields(type).get(fieldName);
        }

        throw new IllegalArgumentException(fieldName);
    }

    public static Map<String, Field> getAllFields(final Class<?> _class) {
        if (cache.get(_class.getName()) != null) return cache.get(_class.getName());

        Map<String, Field> fields = getAllFields(new HashMap<>(), _class);
        cache.put(_class.getName(), fields);

        return cache.get(_class.getName());
    }

    private static Map<String, Field> getAllFields(Map<String, Field> fields, Class<?> _class) {
        for (Field field : _class.getDeclaredFields()) {
            fields.put(field.getName(), field);
        }

        if (_class.getSuperclass() != null) {
            return getAllFields(fields, _class.getSuperclass());
        }

        return fields;
    }

    public static void setFieldValue(Object object, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    public static Object getValue(Object object, String fieldName) throws IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    public static <T extends Annotation> T getAnnotation(Class<?> type, Class<T> annotationType) {

        if (type.isAnnotationPresent(annotationType)) {
            return type.getAnnotation(annotationType);
        } else if (type.getSuperclass() != null) {
            return getAnnotation(type.getSuperclass(), annotationType);
        }
        return null;
    }

    public static <T> T invoke(String method, final Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (T) o.getClass().getMethod(method, null).invoke(o);
    }
}
