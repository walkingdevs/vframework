package brains.vframework.annotation;

import brains.vframework.functional.Tuple;
import brains.vframework.helpers.Fields;
import brains.vframework.helpers.Reflections;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
public @interface Item {
	String searchProperty() default "id";

	class SearchProperties {
        private static final Map<Class<?>, String> cache = new HashMap<>();

        public static String get(final Class<?> _class) {
            if (!cache.containsKey(_class)) {
                String property = "id";

                Item annotation = Reflections.getAnnotation(_class, Item.class);
                if (annotation != null) {
                    property = annotation.searchProperty();
                }
                cache.put(_class, property);
            }
            return cache.get(_class);
        }
    }

    class Properties {
        private static final Map<Class<?>, List<Tuple<Field, Property>>> cache = new HashMap<>();

        public static List<Tuple<Field, Property>> ofClass(final Class<?> _class) {
            if (!cache.containsKey(_class)) {
                List<Tuple<Field, Property>> list = Fields.ofClass(_class).stream()
                        .filter(field -> field.getAnnotation(Property.class) != null)
                        .map(field -> new Tuple<>(field, field.getAnnotation(Property.class))).collect(Collectors.toList());
                cache.put(_class, list);
            }
            return cache.get(_class);
        }
    }
}
