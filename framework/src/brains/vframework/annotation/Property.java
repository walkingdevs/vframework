package brains.vframework.annotation;

import brains.vframework.functional.Tuple;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

	boolean read() default false;
	boolean write() default true;
	int priority() default 999;
    boolean searchable() default false;
    boolean additional() default false;

    class Prioritized {
        private static final Map<Class<?>, List<String>> readables = new HashMap<>();
        private static final Map<Class<?>, List<String>> writables = new HashMap<>();

        public static List<String> readables(final Class<?> _class) {
            if (!readables.containsKey(_class)) {
                readables.put(_class, Prioritized.names(Readables.ofClass(_class)));
            }
            return readables.get(_class);
        }

        public static List<String> writables(final Class<?> _class) {
            if (!writables.containsKey(_class)) {
                writables.put(_class, Prioritized.names(Writables.ofClass(_class)));
            }
            return writables.get(_class);
        }

        public static List<String> names(final List<Tuple<Field, Property>> toSort) {
            return fields(toSort).stream().map(tupple -> tupple.one.getName()).collect(Collectors.toList());
        }

        public static List<Tuple<Field, Property>> fields(final List<Tuple<Field, Property>> toSort) {
            TreeMap<Integer, Tuple<Field, Property>> sortable = new TreeMap<>();
            toSort.forEach(tupple -> sortable.put(tupple.two.priority(), tupple));
            SortedSet<Integer> keys = new TreeSet<>(sortable.keySet());
            return keys.stream().map(sortable::get).collect(Collectors.toList());
        }
    }

    class Writables {
        private static final Map<Class<?>, List<Tuple<Field, Property>>> cache = new HashMap<>();

        public static List<String> names(final Class<?> _class) {
            return ofClass(_class).stream().map(tupple -> tupple.one.getName()).collect(Collectors.toList());
        }

        public static List<Tuple<Field, Property>> ofClass(final Class<?> _class) {
            if (!cache.containsKey(_class)) {
                cache.put(_class, Item.Properties.ofClass(_class).stream().filter(tupple -> tupple.two.write()).collect(Collectors.toList()));
            }
            return cache.get(_class);
        }
    }

    class Readables {
        private static final Map<Class<?>, List<Tuple<Field, Property>>> cache = new HashMap<>();

        public static List<String> names(final Class<?> _class) {
            return ofClass(_class).stream().map(tupple -> tupple.one.getName()).collect(Collectors.toList());
        }

        public static List<Tuple<Field, Property>> ofClass(final Class<?> _class) {
            if (!cache.containsKey(_class)) {
                cache.put(_class, Item.Properties.ofClass(_class).stream().filter(tupple -> tupple.two.read()).collect(Collectors.toList()));
            }
            return cache.get(_class);
        }
    }

    class Searchables {
        private static final Map<Class<?>, List<Tuple<Field, Property>>> cache = new HashMap<>();

        public static List<String> names(final Class<?> _class) {
            return ofClass(_class).stream().map(tupple -> tupple.one.getName()).collect(Collectors.toList());
        }

        public static List<String> namesAll(final Class<?> _class) {
            return ofClassAll(_class, null).stream().map(s -> {
                if (s.startsWith(".")) {
                    return s.substring(1);
                }
                return s;
            }).collect(Collectors.toList());
        }

        public static List<Tuple<Field, Property>> ofClass(final Class<?> _class) {
            if (!cache.containsKey(_class)) {
                cache.put(_class, Item.Properties.ofClass(_class).stream().filter(tupple -> tupple.two.searchable()).collect(Collectors.toList()));
            }
            return cache.get(_class);
        }

        private static List<String> ofClassAll(Class<?> _class, String base) {
            List<String> acc = new ArrayList<>();
            Item.Properties.ofClass(_class).stream().filter(tupple -> tupple.two.searchable()).forEach(fieldPropertyTuple -> {
                if (Types.isReference(fieldPropertyTuple.one)) {
                    acc.addAll(ofClassAll(fieldPropertyTuple.one.getType(), fieldPropertyTuple.one.getName()));
                } else {
                    if (base != null) {
                        acc.add(base + "." + fieldPropertyTuple.one.getName());
                    } else {
                        acc.add(fieldPropertyTuple.one.getName());
                    }
                }
            });
            return acc;
        }
    }

    class Types {
        public static boolean isBasic(final Field field) {
            return field.getType().isPrimitive() || (!isList(field) && !isReference(field));
        }

        public static boolean isReference(final Field field) {
            return field.isAnnotationPresent(ManyToOne.class);
        }

        public static boolean isList(final Field field) {
            return field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(Pivot.class);
        }
    }
}
