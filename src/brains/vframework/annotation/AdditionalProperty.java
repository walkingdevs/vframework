package brains.vframework.annotation;

import brains.vframework.helpers.Reflections;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
public @interface AdditionalProperty {

    class Reflect {
        private static final Map<Class<?>, List<Field>> cache = new HashMap<>();

        public static List<String> names(final Class<?> _class) {
            return fields(_class).stream().map(Field::getName).collect(Collectors.toList());
        }

        public static List<Field> fields(final Class<?> _class) {
            if (!cache.containsKey(_class)) {
                List<Field> list = new ArrayList<>();

                Reflections.getAllFields(_class).forEach((name, field) -> {
                    Property additionalProperty = field.getAnnotation(Property.class);
                    if (additionalProperty != null && additionalProperty.additional()) {
                        list.add(field);
                    }
                });

                cache.put(_class, list);
            }
            return cache.get(_class);
        }
    }
}
